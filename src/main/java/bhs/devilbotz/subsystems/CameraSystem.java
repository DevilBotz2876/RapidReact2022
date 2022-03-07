package bhs.devilbotz.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.MjpegServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoMode;
import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CameraSystem extends SubsystemBase {
    private static final VideoMode VIDEO_MODE = new VideoMode(VideoMode.PixelFormat.kMJPEG, 360, 270, 20);
    private static final int COMPRESSION_LEVEL = 35;
    private UsbCamera cameraOne = null;
    private UsbCamera cameraTwo = null;
    private MjpegServer videoSink = null;


    public CameraSystem() {
        try {
            cameraOne = CameraServer.startAutomaticCapture(0);
            cameraTwo = CameraServer.startAutomaticCapture(1);
            final double ratio = cameraOne.getVideoMode().height / (double) cameraOne.getVideoMode().width;
            cameraOne.setVideoMode(VIDEO_MODE.pixelFormat, VIDEO_MODE.width, (int) (VIDEO_MODE.width * ratio), VIDEO_MODE.fps);
            cameraOne.setConnectionStrategy(VideoSource.ConnectionStrategy.kAutoManage);
            cameraTwo.setVideoMode(VIDEO_MODE.pixelFormat, VIDEO_MODE.width, (int) (VIDEO_MODE.width * ratio), VIDEO_MODE.fps);
            cameraTwo.setConnectionStrategy(VideoSource.ConnectionStrategy.kAutoManage);

            videoSink = CameraServer.addSwitchedCamera("Toggle Camera");

            final VideoSource source = videoSink.getSource();

            if (COMPRESSION_LEVEL >= 0) {
                videoSink.setCompression(COMPRESSION_LEVEL);
                videoSink.setDefaultCompression(COMPRESSION_LEVEL);
            }
            videoSink.setSource(cameraTwo);
            Shuffleboard.getTab("Drive").add("Camera", source).withSize(4, 4).withPosition(2, 0);
        } catch (Exception e) {
            System.out.println("Camera not found");
        }
    }

    public void setCameraOne() {
        videoSink.setSource(cameraOne);
    }

    public void setCameraTwo() {
        videoSink.setSource(cameraTwo);
    }

    public int getCameraIndex() {
        if (videoSink.getSource().getName().contains("USB Camera 1")) {
            return 1;
        } else {
            return 0;
        }
    }
}
