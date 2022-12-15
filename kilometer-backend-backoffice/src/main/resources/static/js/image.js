var appVue = new Vue({
    el: '#wrap',
    data: {
        listImageUrl: '',
        thumbnailImageUrl: '',
        detailImageUrl: '',
        keyVisualImageUrl: '',
        fileIndex: 0,
        currentIndex: 0,
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
                this.thumbnailImageUrl = response.data;
                $('#thumbnailPreview').html("<img style=\"width: 100px; height: 100px;\" src=" + this.thumbnailImageUrl + ">");
                $('#thumbnailImageUrl').val(this.thumbnailImageUrl);
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
        },

        checkDetailImageS3Upload: function (id) {
            this.currentIndex = id;
        },

        detailImageS3Upload: function (event) {
            let formData = new FormData();
            let file = event.target.files[0];
            let index = Number($(event.target).attr('data-index'));
            formData.append("file", file);
            axios.post('/form/image/default', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }).then(function (response) {
                this.detailImageUrl = response.data;
                $('#detailPreview\\[' + index + '\\]').html("<img style=\"width: 100px; height: 100px;\" src=" + this.detailImageUrl + ">");
                $('#detailImageUrls\\[' + index + '\\]').val(this.detailImageUrl);
            })
        },

        addFile: function () {
            let index = ++this.fileIndex;

            const fileHtml = `
        <div data-name="fileDiv">
            <input type="hidden" id="detailImageUrls[${this.fileIndex}]" name="detailImageUrls[${this.fileIndex}]">
            <input type="file" id="inputDetailImage[${this.fileIndex}]" data-index="${this.fileIndex}">
            <button type="button" onclick="removeFile(this)"> 삭제 </button>
            <div id="detailPreview[${this.fileIndex}]"></div>
        </div>
	`;
            $('#btnDiv').append(fileHtml);
            console.log(index);
            $(document).on('change', '#inputDetailImage\\[' + index + '\\]', this.detailImageS3Upload);
        },
    }
})