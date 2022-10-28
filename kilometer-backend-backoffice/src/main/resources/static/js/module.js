var appVue = new Vue({
    el: '#wrap',
    data: {
        modules: [],
        moduleType: [],
        deleteModulesId: [],
    },

    mounted: function () {
        this.init();
    },

    methods: {
        init: function () {
            let $this = this;

            axios.get('/home/modules/list')
                .then(function (response) {
                    $this.modules = response.data;
                    $this.modules.forEach((module) => {
                        $this.checkReadOnly(module);
                    });
                })
                .catch(function (error) {
                    console.log(error);
                });

            axios.get('/home/modules/type')
                .then(function (response) {
                    $this.moduleType = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                });
        },

        checkReadOnly: function (module) {
            let moduleName = module.moduleName;
            if (moduleName === 'KEY_VISUAL') {
                module.titleDisabled = true;
                module.extraDataDisabled = true;
            } else if (moduleName === 'SWIPE_ITEM') {
                module.titleDisabled = false;
                module.extraDataDisabled = false;
            } else {
                module.titleDisabled = false;
                module.extraDataDisabled = true;
            }
        },

        addRow: function () {
            this.modules.push({
                id: '',
                exposureOrderNumber: '',
                moduleName: 'EMPTY',
                upperModuleTitle: '',
                lowerModuleTitle: '',
                extraData: ''
            });
        },

        removeRow: function (index) {
            let $this = this;

            if (confirm("삭제하시겠습니까?") === false) {
                return;
            }

            $this.deleteModulesId.push($this.modules[index].id);
            $this.modules.splice(index, 1);
        },

        change: function (index) {
            let $this = this;
            let moduleName = $this.modules[index].moduleName;
            if (moduleName === 'KEY_VISUAL') {
                $this.modules[index].titleDisabled = true;
                $this.modules[index].extraDataDisabled = true;
            } else if (moduleName === 'SWIPE_ITEM') {
                $this.modules[index].titleDisabled = false;
                $this.modules[index].extraDataDisabled = false;
            } else {
                $this.modules[index].titleDisabled = false;
                $this.modules[index].extraDataDisabled = true;
            }
        },

        deleteModules: function () {
            let $this = this;
            let data = JSON.stringify({
                deleteModuleList: $this.deleteModulesId
            })
            axios.post('/home/modules/delete', data, {
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(function (response) {
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    }
})