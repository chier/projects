//从服务器获取进度信息
function refreshProgress() {
    UploadMonitor.getUploadInfo(updateProgress);
}

//更新上传进度信息
function updateProgress(uploadInfo) {
    if (uploadInfo.inProgress) {
        //禁用“开始上传”按钮和文件选择框
        document.getElementById("uploadbutton").disabled = true;
        document.getElementById("file1").disabled = true;

        //计算上传进度百分比
        var progressPercent = Math.ceil((uploadInfo.bytesRead / uploadInfo.totalSize) * 100);

        //更新上传进度文字信息
        document.getElementById("uploadBarText").innerHTML = "上传进度：" + progressPercent + "%";

        //更新上传进度条宽度
        document.getElementById("uploadBarBoxContent").style.width = parseInt(progressPercent * 3.5) + "px";

        window.setTimeout("refreshProgress()", 1000);   //等待1秒后继续更新上传进度信息
    } else {
        //取消“开始上传”按钮和文件选择框的禁用状态
        document.getElementById("uploadbutton").disabled = false;
        document.getElementById("file1").disabled = false;
    }
}

//开始监控上传进度
function startProgress() {
    //显示上传进度div，禁用“开始上传”按钮
    document.getElementById("uploadBar").style.display = "block";
    document.getElementById("uploadBarText").innerHTML = "上传进度：0%";
    document.getElementById("uploadbutton").disabled = true;

    //稍等一下确保上传过程已经开始
    window.setTimeout("refreshProgress()", 1000);
}
