var appVue = new Vue({
    el: '#wrap',
    data: {
        modules: [],
        moduleType: [],
        deleteModulesId: [],
        errors: [],
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
                    // for (const module of $this.modules) {
                    //     module.checkbox = false;
                    //     $this.checkReadOnly(module);
                    // }
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

        removeRow: function () {
            let $this = this;

            if (confirm("삭제하시겠습니까?") === false) {
                return;
            }

            for (let i = $this.modules.length - 1; i >= 0; i--) {
                if ($this.modules[i].checkbox === true) {
                    if ($this.modules[i].id !== '') {
                        $this.deleteModulesId.push($this.modules[i].id);
                    }
                    $this.modules.splice(i, 1);
                }
            }
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

        save: function () {
            let $this = this;
            let moduleList = {
                moduleList: $this.modules
            }
            let data = JSON.stringify(moduleList);

            axios.post('/home/modules/edit', data, {
                headers: {
                    'Content-Type': 'application/json'}
            })
                .then(function (response) {
                    $this.errors = response.data;
                    console.log(response.data);
                    console.log($this.errors);
                    $this.checkErrors();
                })
                .catch(function (error) {
                    console.log(error);
                })
        },

        checkErrors: function () {
            let $this = this;
            let errors = $this.errors;

            if (errors.length !== 0) {
                let printError = '';
                errors.forEach((error) => {
                    printError += error + '\n';
                })
                alert(printError);
            } else {
                $this.deleteModule();
            }
        },

        deleteModule: function () {
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
                    console.log(response);
                    $this.goList();
                })
                .catch(function (error) {
                    console.log(error);
                });
        },

        goList: function () {
            location.href = '/home/modules';
        }
    }
})