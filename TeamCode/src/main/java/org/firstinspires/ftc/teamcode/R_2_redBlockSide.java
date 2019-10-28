package org.firstinspires.ftc.teamcode;


import org.firstinspires.ftc.robotcore.external.ClassFactory;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

@Autonomous(name="R_2_redBlockSide", group="zzz")
public class R_2_redBlockSide extends R2_skyStoneClass {
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false  ;

    public String positionSkystone = "";

    private static final String VUFORIA_KEY =
            "ASGFjiL/////AAABmaZPknqZXkmmtTGwg+uEgu2C3gZHn4ty9G5tCQODMMEmOGMJ//A61QXRzXTQ325wH/0YePAvHwhvQi3WvEl7/uo7TXACAxQck03MAZt/TLhbD19Q39Op9sr1cMaNNbBNV8EmjqvuP9zs8FvgYKXK9s/llYal1b9PAHbyQcA2pFBX89JTcP+gkYR8ZtN6Ce5GuuKfdeMM+x4BIuIDyFqO/cAbQ2YYqGbhssjM5Qh3gZyFDtJzFm4jnY33EI1wFRfKguwU5W58i9TLty4S/OfSUV6B7n+6iOfJgYrG6d26sbwkklAlH2AhNuRtNbTjworCq2IFczGeJwSC+IsFcy70pE4L2bhTSyogln8q0RKulZ/r ";

    private static final float mmPerInch        = 25.4f;
    private static final float mmTargetHeight   = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Constant for Stone Target
    private static final float stoneZ = 2.00f * mmPerInch;

    // Constants for the center support targets
    private static final float bridgeZ = 6.42f * mmPerInch;
    private static final float bridgeY = 23 * mmPerInch;
    private static final float bridgeX = 5.18f * mmPerInch;
    private static final float bridgeRotY = 59;                                 // Units are degrees
    private static final float bridgeRotZ = 180;

    // Constants for perimeter targets
    private static final float halfField = 72 * mmPerInch;
    private static final float quadField  = 36 * mmPerInch;

    // Class Members
    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;
    private boolean targetVisible = false;
    private float phoneXRotate    = 0;
    private float phoneYRotate    = 0;
    private float phoneZRotate    = 0;
    private boolean leftBlock = false;
    private boolean rightBlock = false;
    private boolean centerBlock= false;

    protected ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        initRobot(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.addData(">", "Press Play to start");
        telemetry.update();

        waitForStart();
        runtime.reset();

        //set encoder
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // rightArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //rightArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //leftArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //leftArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if (opModeIsActive()) {
            getToPosition();
            getSkyStone();
        }
    }

    public void getToPosition() {
      strafeRight(2,8 );
      driveStraight(2,6);
    }

    public void getSkyStone() {
        long start_Time = System.currentTimeMillis();
        long end_time = start_Time + 2000;
        while (System.currentTimeMillis() < end_time) {

                int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
                VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

                // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

                parameters.vuforiaLicenseKey = VUFORIA_KEY;
                parameters.cameraDirection = CAMERA_CHOICE;

                //  Instantiate the Vuforia engine
                vuforia = ClassFactory.getInstance().createVuforia(parameters);


                VuforiaTrackables targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");

                VuforiaTrackable stoneTarget = targetsSkyStone.get(0);
                stoneTarget.setName("Stone Target");
                VuforiaTrackable blueRearBridge = targetsSkyStone.get(1);
                blueRearBridge.setName("Blue Rear Bridge");
                VuforiaTrackable redRearBridge = targetsSkyStone.get(2);
                redRearBridge.setName("Red Rear Bridge");
                VuforiaTrackable redFrontBridge = targetsSkyStone.get(3);
                redFrontBridge.setName("Red Front Bridge");
                VuforiaTrackable blueFrontBridge = targetsSkyStone.get(4);
                blueFrontBridge.setName("Blue Front Bridge");
                VuforiaTrackable red1 = targetsSkyStone.get(5);
                red1.setName("Red Perimeter 1");
                VuforiaTrackable red2 = targetsSkyStone.get(6);
                red2.setName("Red Perimeter 2");
                VuforiaTrackable front1 = targetsSkyStone.get(7);
                front1.setName("Front Perimeter 1");
                VuforiaTrackable front2 = targetsSkyStone.get(8);
                front2.setName("Front Perimeter 2");
                VuforiaTrackable blue1 = targetsSkyStone.get(9);
                blue1.setName("Blue Perimeter 1");
                VuforiaTrackable blue2 = targetsSkyStone.get(10);
                blue2.setName("Blue Perimeter 2");
                VuforiaTrackable rear1 = targetsSkyStone.get(11);
                rear1.setName("Rear Perimeter 1");
                VuforiaTrackable rear2 = targetsSkyStone.get(12);
                rear2.setName("Rear Perimeter 2");

                // For convenience, gather together all the trackable objects in one easily-iterable collection */
                List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
                allTrackables.addAll(targetsSkyStone);


                stoneTarget.setLocation(OpenGLMatrix
                        .translation(0, 0, stoneZ)
                        .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

                //Set the position of the bridge support targets with relation to origin (center of field)
                blueFrontBridge.setLocation(OpenGLMatrix
                        .translation(-bridgeX, bridgeY, bridgeZ)
                        .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, bridgeRotZ)));

                blueRearBridge.setLocation(OpenGLMatrix
                        .translation(-bridgeX, bridgeY, bridgeZ)
                        .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, bridgeRotZ)));

                redFrontBridge.setLocation(OpenGLMatrix
                        .translation(-bridgeX, -bridgeY, bridgeZ)
                        .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, 0)));

                redRearBridge.setLocation(OpenGLMatrix
                        .translation(bridgeX, -bridgeY, bridgeZ)
                        .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, 0)));

                //Set the position of the perimeter targets with relation to origin (center of field)
                red1.setLocation(OpenGLMatrix
                        .translation(quadField, -halfField, mmTargetHeight)
                        .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

                red2.setLocation(OpenGLMatrix
                        .translation(-quadField, -halfField, mmTargetHeight)
                        .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

                front1.setLocation(OpenGLMatrix
                        .translation(-halfField, -quadField, mmTargetHeight)
                        .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

                front2.setLocation(OpenGLMatrix
                        .translation(-halfField, quadField, mmTargetHeight)
                        .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

                blue1.setLocation(OpenGLMatrix
                        .translation(-quadField, halfField, mmTargetHeight)
                        .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

                blue2.setLocation(OpenGLMatrix
                        .translation(quadField, halfField, mmTargetHeight)
                        .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

                rear1.setLocation(OpenGLMatrix
                        .translation(halfField, quadField, mmTargetHeight)
                        .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

                rear2.setLocation(OpenGLMatrix
                        .translation(halfField, -quadField, mmTargetHeight)
                        .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));


                // We need to rotate the camera around it's long axis to bring the correct camera forward.
                if (CAMERA_CHOICE == BACK) {
                    phoneYRotate = -90;
                } else {
                    phoneYRotate = 90;
                }

                // Rotate the phone vertical about the X axis if it's in portrait mode
                if (PHONE_IS_PORTRAIT) {
                    phoneXRotate = 90;
                }

                // Next, translate the camera lens to where it is on the robot.
                // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
                final float CAMERA_FORWARD_DISPLACEMENT = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot center
                final float CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
                final float CAMERA_LEFT_DISPLACEMENT = 0;     // eg: Camera is ON the robot's center line

                OpenGLMatrix robotFromCamera = OpenGLMatrix
                        .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                        .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

                /**  Let all the trackable listeners know where the phone is.  */
                for (VuforiaTrackable trackable : allTrackables) {
                    ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
                }

                // WARNING:
                // In this sample, we do not wait for PLAY to be pressed.  Target Tracking is started immediately when INIT is pressed.
                // This sequence is used to enable the new remote DS Camera Preview feature to be used with this sample.
                // CONSEQUENTLY do not put any driving commands in this loop.
                // To restore the normal opmode structure, just un-comment the following line:

                // waitForStart();

                // Note: To use the remote camera preview:
                // AFTER you hit Init on the Driver Station, use the "options menu" to select "Camera Stream"
                // Tap the preview window to receive a fresh image.

                targetsSkyStone.activate();
                while (!isStopRequested()) {

                    // check all the trackable targets to see which one (if any) is visible.
                    targetVisible = false;
                    for (VuforiaTrackable trackable : allTrackables) {
                        if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                            telemetry.addData("Visible Target", trackable.getName());

                            if (trackable.getName().equals("Stone Target")) {
                                telemetry.addLine("Sky Stone is Visible");

                            }
                            targetVisible = true;


                            // getUpdatedRobotLocation() will return null if no new information is available since
                            // the last time that call was made, or if the trackable is not currently visible.
                            OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                            if (robotLocationTransform != null) {
                                lastLocation = robotLocationTransform;
                            }
                            break;
                        }
                    }
// Provide feedback as to where the robot is located (if we know).


                    if (targetVisible) {
                        // express position (translation) of robot in inches.
                        VectorF translation = lastLocation.getTranslation();
                        telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                                translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

                        double yPosition = translation.get(1);

                        if (yPosition < -70) {
                             positionSkystone = "Left";
                             leftBlock = true;

                        } else if (yPosition > 20) {
                            positionSkystone = "Right";
                            rightBlock = true;

                        } else {
                            positionSkystone = "center";
                            centerBlock = true;

                        }
                        telemetry.addData("positionSkystone", positionSkystone);
                        // express the rotation of the robot in degrees.
                        Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
                        telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
                    } else {
                        telemetry.addData("Visible Target", "none");
                    }
                    telemetry.update();
                }

                // Disable Tracking when we are done;
                targetsSkyStone.deactivate();
        }
        if(leftBlock)
        {
            strafeLeft(2,6 );
            driveStraight(2, 8);
            rotate(2, 16);
        }
        else if (rightBlock)
        {
            strafeLeft(2,6 );
            driveStraight(2,8);
            rotate(2, 16);
        }
        else if (centerBlock)
        {
            strafeLeft(1, 4);
            driveStraight(2,8);
            rotate(2,16);

        }
    }



    }



