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
    private UsbCamera transferCamera = null;
    private UsbCamera driveCamera = null;
    private MjpegServer videoSink = null;


    public CameraSystem() {
        try {
            transferCamera = CameraServer.startAutomaticCapture(0);
            transferCamera.setBrightness(12);
            driveCamera = CameraServer.startAutomaticCapture(1);
            driveCamera.setBrightness(20);
            final double ratio = transferCamera.getVideoMode().height / (double) transferCamera.getVideoMode().width;
            transferCamera.setVideoMode(VIDEO_MODE.pixelFormat, VIDEO_MODE.width, (int) (VIDEO_MODE.width * ratio), VIDEO_MODE.fps);
            transferCamera.setConnectionStrategy(VideoSource.ConnectionStrategy.kAutoManage);
            driveCamera.setVideoMode(VIDEO_MODE.pixelFormat, VIDEO_MODE.width, (int) (VIDEO_MODE.width * ratio), VIDEO_MODE.fps);
            driveCamera.setConnectionStrategy(VideoSource.ConnectionStrategy.kAutoManage);

            videoSink = CameraServer.addSwitchedCamera("Toggle Camera");

            final VideoSource source = videoSink.getSource();

            if (COMPRESSION_LEVEL >= 0) {
                videoSink.setCompression(COMPRESSION_LEVEL);
                videoSink.setDefaultCompression(COMPRESSION_LEVEL);
            }
            videoSink.setSource(driveCamera);
            Shuffleboard.getTab("Drive").add("Camera", source).withSize(5, 5).withPosition(2, 0);
        } catch (Exception e) {
            System.out.println("Camera not found");
        }
    }

    public void setCameraOne() {
        videoSink.setSource(transferCamera);
    }

    public void setCameraTwo() {
        videoSink.setSource(driveCamera);
    }

    public int getCameraIndex() {
        if (videoSink.getSource().getName().contains("USB Camera 1")) {
            return 1;
        } else {
            return 0;
        }
    }
}
