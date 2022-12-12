var appVue = new Vue({
    el: '#wrap',
    data: {
        keyVisuals: [],
    },

    mounted: function () {
        this.init();
    },

    methods: {
        init: function () {
            let $this = this;

            axios.get('/home/key_visual/list')
                .then(function (response) {
                    $this.keyVisuals = response.data;
                    for (let i = 0; i < $this.keyVisuals.length; i++) {
                        $this.keyVisuals[i].imageTag = "<img style=\"width: 100px; height: 100px;\" src=" + $this.keyVisuals[i].imageUrl + ">";
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
        },

        keyVisualImageS3Upload: function (index, event) {
            let $this = this;

            let formData = new FormData();
            let file = event.target.files[0];
            formData.append('file', file);
            axios.post('/form/image/default', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }).then(function (response) {
                this.keyVisualImageUrl = response.data;
                $this.keyVisuals[index].imageTag = "<img style=\"width: 100px; height: 100px;\" src=" + this.keyVisualImageUrl + ">";
                $this.keyVisuals[index].imageUrl = this.keyVisualImageUrl;
            })
        },

        keyVisualImageDelete: function (index) {
            let $this = this;

            $this.keyVisuals[index].imageUrl = '';
            $this.keyVisuals[index].imageTag = '';
            $("#fileName" + index).val('');
        },
    }
})