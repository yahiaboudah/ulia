package com.bouda.ulia.activites.sensors;

import static com.bouda.ulia.utils.Misc.toast;
import static com.bouda.underscore.UnderScore._;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaRecorder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Map;

@SuppressLint("StaticFieldLeak")
public class AudioRecorder {

    private final static String LOG_TAG = "AudioRecorder";
    private final Context context;
    private final MediaRecorder recorder;
    private final String saveFile;
    private boolean recorderOn;
    private static AudioRecorder activeAudioRecorderInstance = null;

    private final static String RECORDER_ON_MESSAGE = "Recording...";
    private final static String RECORDER_OFF_MESSAGE = "Recording stopped...";

    public static AudioRecorder getActiveAudioRecorderInstance() {
        return activeAudioRecorderInstance;
    }

    public AudioRecorder(Context context, int audioSource, int outputFormat,
                         String outputFile, int audioEncoder){

        this.context = context;
        this.saveFile = outputFile;
        recorder = new MediaRecorder();
        recorder.setAudioSource(audioSource);
        recorder.setOutputFormat(outputFormat);
        recorder.setOutputFile(outputFile);
        recorder.setAudioEncoder(audioEncoder);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare method failed");
        }

        recorderOn = false;
    }

    public boolean getRecorderStatus(){
        return recorderOn;
    }

    public void setRecorderStatus(boolean status){
        this.recorderOn = status;
    }

    public MediaRecorder getRecorder(){
        return this.recorder;
    }

    public String getSaveFile(){
        return this.saveFile;
    }

    public Context getContext(){ return context; }

    public void start(){
        if(!recorderOn) {
            this.recorder.start();
            recorderOn = true;
        }
    }

    public void stop(){
        if(recorderOn){
            this.recorder.stop();
            this.recorder.release();
            recorderOn = false;
        }
    }

    public static void record(Context context){

        activeAudioRecorderInstance = new AudioRecorder.Builder(context).build();

        if(!activeAudioRecorderInstance.getRecorderStatus()) {
            activeAudioRecorderInstance.getRecorder().start();
            toast(context, RECORDER_ON_MESSAGE);
            activeAudioRecorderInstance.setRecorderStatus(true);
        }
        else{
            Log.i(AudioRecorder.LOG_TAG, "Recorder is already on!");
        }
    }

    public static String save(){
        String saveFile = activeAudioRecorderInstance.getSaveFile();
        Context context = activeAudioRecorderInstance.getContext();

        if(activeAudioRecorderInstance.getRecorderStatus()){
            activeAudioRecorderInstance.getRecorder().stop();
            activeAudioRecorderInstance.getRecorder().release();
            toast(context, RECORDER_OFF_MESSAGE);
            activeAudioRecorderInstance.setRecorderStatus(false);

            activeAudioRecorderInstance = null;
        }

        return saveFile;
    }

    public static void onActivate(Context context, boolean toast){

        if(activeAudioRecorderInstance == null) {
            if(toast)
                Toast.makeText(context, "Starting recording..", Toast.LENGTH_LONG).show();
            record(context);
        }
        else {
            if(toast)
                Toast.makeText(context, "Saving recording in: " +
                        activeAudioRecorderInstance.getSaveFile(), Toast.LENGTH_LONG).show();
            save();
        }
    }

    public static class Builder{

        private final Map<Integer, String> formatExtensionsMap = _(
                MediaRecorder.OutputFormat.AAC_ADTS , "adts",
                MediaRecorder.OutputFormat.AMR_NB, "amr",
                MediaRecorder.OutputFormat.AMR_WB, "awb",
                MediaRecorder.OutputFormat.MPEG_2_TS, "m2ts",
                MediaRecorder.OutputFormat.MPEG_4, "mp4",
                MediaRecorder.OutputFormat.OGG, "ogg",
                MediaRecorder.OutputFormat.THREE_GPP, "3gp",
                MediaRecorder.OutputFormat.WEBM, "webm"
        );

        private String getFilePath(String dirPath, String fileName){
            return String.format("%s/%s.%s", dirPath, fileName == null? "audioRecorderTest":fileName,
                    formatExtensionsMap.get(this.outputFormat));
        }

        private String getCacheDirPath(Context context){
            return context.getExternalCacheDir().getAbsolutePath();
        }

        private Context context;
        public Builder(Context context){
            this.context = context;
            this.outputFile = getFilePath(getCacheDirPath(this.context), null);
        }

        private int audioSource = MediaRecorder.AudioSource.MIC;
        private int outputFormat = MediaRecorder.OutputFormat.THREE_GPP;
        private String outputFile;
        private int audioEncoder = MediaRecorder.AudioEncoder.AMR_NB;

        public Builder audioSource(int audioSource){
            this.audioSource = audioSource;
            return this;
        }

        public Builder outputFormat(int outputFormat){
            this.outputFormat = outputFormat;
            return this;
        }

        public Builder fileName(String fileName){
            this.outputFile = getFilePath(getCacheDirPath(this.context), fileName);
            return this;
        }

        public Builder outputFile(String outputFile){
            this.outputFile = outputFile;
            return this;
        }

        public Builder audioEncoder(int audioEncoder){
            this.audioEncoder = audioEncoder;
            return this;
        }

        public AudioRecorder build(){
            Log.i("AAA", this.outputFile);
            return new AudioRecorder(this.context, this.audioSource, this.outputFormat,
                    this.outputFile, this.audioEncoder);
        }
    }
}