var appVue = new Vue({
    el: '#wrap',
    data: {
        listImageUrl: '',
        thumbnailImage: '',
    },
    methods: {
        listImageS3Upload: function (event) {
            let formData = new FormData();
            let file = event.target.files[0];
            formData.append("file", file);
            axios.post('/form/image/default', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }).then(function (response) {
                this.listImageUrl = response.data;
                $('#listPreview').html("<img style=\"width: 100px; height: 100px;\" src=" + this.listImageUrl + ">");
                $('#listImageUrl').val(this.listImageUrl);
            })
        },

        thumbnailImageS3Upload: function (event) {
            let formData = new FormData();
            let file = event.target.files[0];
            formData.append("file", file);
            axios.post('/form/image/default', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }).then(function (response) {
                this.thumbnailImage = response.data;
                $('#thumbnailPreview').html("<img style=\"width: 100px; height: 100px;\" src=" + this.thumbnailImage + ">");
                $('#thumbnailImageUrl').val(this.thumbnailImage);
            })
        },

        listImageDelete: function (event) {
            let $input = $("#inputListImage");
            let $preview = $('#listPreview');
            let $listImageUrl = $("#listImageUrl")
            this.resetInputFile($input, $preview, $listImageUrl);
        },

        thumbnailImageDelete: function (event) {
            let $input = $("#inputThumbnailImage");
            let $preview = $('#thumbnailPreview');
            let $listImageUrl = $("#thumbnailImageUrl")
            this.resetInputFile($input, $preview, $listImageUrl);
        },

        // 등록 이미지 삭제 ( input file reset )
        resetInputFile: function ($input, $preview, $listImageUrl) {
            $input.val("");
            $listImageUrl.val("");
            $preview.empty();
        }
    }
})