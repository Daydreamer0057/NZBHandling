package youtube;

import com.github.kiulian.downloader.Config;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestPlaylistInfo;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.playlist.PlaylistInfo;
import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class YoutubeDownload {
    String playlistId = "https://www.youtube.com/@TemperanceOfficialChannel/videos"; // for url https://www.youtube.com/playlist?list=abc12345
    File outputDir = new File("z://Documentaires/Music Video/Temperance");

    public YoutubeDownload() {
        YoutubeDownloader downloader = init();
        ArrayList<PlaylistVideoDetails> listeVideos = getplaylist(downloader);

        for(PlaylistVideoDetails videoDetails : listeVideos) {
            downloadVideo(videoDetails.videoId(),downloader);
        }
    }

    public static void main(String[] args) {
        YoutubeDownload y = new YoutubeDownload();
    }

    public YoutubeDownloader init(){
        Config config = new Config.Builder()
                .executorService(Executors.newCachedThreadPool()) // for async requests, default Executors.newCachedThreadPool()
                .maxRetries(1) // retry on failure, default 0
                .header("Accept-language", "en-US,en;") // extra request header
                .build();
        return new YoutubeDownloader(config);

    }

    public ArrayList<PlaylistVideoDetails> getplaylist(YoutubeDownloader downloader){
        ArrayList<PlaylistVideoDetails> listeVideo = new ArrayList<>();

        RequestPlaylistInfo request = new RequestPlaylistInfo(playlistId);
        Response<PlaylistInfo> response = downloader.getPlaylistInfo(request);
        PlaylistInfo playlistInfo = response.data();

        for(PlaylistVideoDetails details : playlistInfo.videos()) {
            if(details.lengthSeconds()>=180){
                listeVideo.add(details);
            }
        }
        return listeVideo;
    }

    public void downloadVideo(String videoId, YoutubeDownloader downloader){
// sync parsing
        RequestVideoInfo requestParsing = new RequestVideoInfo(videoId);
        Response<VideoInfo> response = downloader.getVideoInfo(requestParsing);
        VideoInfo video = response.data();

// get best format
        video.bestVideoWithAudioFormat();
        video.bestVideoFormat();
        video.bestAudioFormat();

        Format format = video.bestVideoWithAudioFormat();

// sync downloading
        RequestVideoFileDownload requestDownload = new RequestVideoFileDownload(format)
                // optional params
                .saveTo(outputDir) // by default "videos" directory
                .overwriteIfExists(true); // if false and file with such name already exits sufix will be added video(1).mp4
        Response<File> responseDownload = downloader.downloadVideoFile(requestDownload);
        File data = responseDownload.data();
    }
}

