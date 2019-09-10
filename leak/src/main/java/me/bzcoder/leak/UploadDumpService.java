package me.bzcoder.leak;

import android.util.Log;
import android.widget.Toast;

import com.squareup.leakcanary.AnalysisResult;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.HeapDump;

public class UploadDumpService extends DisplayLeakService {
    private static final String TAG = "UploadDumpService";
    @Override
    protected void afterDefaultHandling(HeapDump heapDump, AnalysisResult result, String leakInfo) {
        if (!result.leakFound || result.excludedLeak) {
            return;
        }
        Log.d(TAG, "afterDefaultHandling: 我开始上传日志了");
        Toast.makeText(this, "我开始上传日志了", Toast.LENGTH_SHORT).show();
    }
}