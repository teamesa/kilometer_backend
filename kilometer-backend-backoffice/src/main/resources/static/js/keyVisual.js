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
                })
                .catch(function (error) {
                    console.log(error);
                });
        },

        keyVisualImageS3Upload: function (event) {
            let formData = new FormData();
            let file = event.target.files[0];
            let index = Number(event.target.id.substring(14));
            formData.append("file", file);
            axios.post('/form/image/default', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }).then(function (response) {
                this.keyVisualImageUrl = response.data;
                $('#keyVisualImage\\[' + index + '\\]').html("<img style=\"width: 100px; height: 100px;\" src=" + this.keyVisualImageUrl + ">");
                $('#keyVisual' + index).val(this.keyVisualImageUrl);
            })
        },

        keyVisualImageDelete: function (event) {
            let index = Number(event.target.id.substring(20));
            let $input = $("#keyVisualIndex\\[" + index + "\\]");
            let $preview = $('#keyVisualImage\\[' + index + '\\]');
            let $listImageUrl = $("#keyVisual" + index);
            this.resetInputFile($input, $preview, $listImageUrl);
        },

        // 등록 이미지 삭제 ( input file reset )
        resetInputFile: function ($input, $preview, $listImageUrl) {
            $input.val("");
            $listImageUrl.val("");
            $preview.empty();
        },
    }
})