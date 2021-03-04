//实现上传文件时的进度判断功能
package com.jibolin.oss.util;

import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;

public class PutObjectProgressListener implements ProgressListener {
    private long bytesWritten = 0;
    private long totalBytes = -1;
    private boolean succeed = false;
    public static int percent;//进度条变量

    @Override
    public void progressChanged(ProgressEvent progressEvent) {
        long bytes = progressEvent.getBytes();
        ProgressEventType eventType = progressEvent.getEventType();
        switch (eventType) {
        case TRANSFER_STARTED_EVENT:
            System.out.println("开始上传");
            break;
        case REQUEST_CONTENT_LENGTH_EVENT:
            this.totalBytes = bytes;
            System.out.println("共计"+this.totalBytes + "字节将上传到云");
            break;
        case REQUEST_BYTE_TRANSFER_EVENT:
            this.bytesWritten += bytes;
            if (this.totalBytes != -1) {
                percent = (int)(this.bytesWritten * 100.0 / this.totalBytes);
                System.out.println("此时已读取字节：" + bytes + "\n" + "上传进度：" +percent + "% （" + this.bytesWritten + "/" + this.totalBytes + "）");
            } else {
                System.out.println("此时已读取字节：" + bytes + "\n" + "上传进度：未知" +"（" + this.bytesWritten + "/...）");
            }
            break;
        case TRANSFER_COMPLETED_EVENT:
            this.succeed = true;
            System.out.println("上传成功，总共传输字节：" + this.bytesWritten);
            break;
        case TRANSFER_FAILED_EVENT:
            System.out.println("上传失败，已传输字节：" + this.bytesWritten);
            break;
        default:
            break;
        }
    }
    //暂时未使用到
    public boolean isSucceed() {
        return succeed;
    }
}