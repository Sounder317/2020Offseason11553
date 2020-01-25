package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import com.qualcomm.robotcore.util.Range;
//look angle 1 and a qaurter
//TODO extend R_2_OPMode instead of R2_skyStoneClass?
@Autonomous(name="R_2_blueBlockSideSC", group="zzz")
public class R_2_blueBlockSideSC extends R_2_OpMode {
    //TODO move some of these constants to setupVuforia() as they are needed only in setupVuforia()
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false;

    private static final String VUFORIA_KEY =
            "AaS8xZz/////AAABmYRAdPhXRkbakiVrxj3AzpQewhQrC2RuGeRbTN9lpwdQgl4/kIrWYRgi64l0le9jJDj15kDxkQUz0EY4/g7exi9CC5nedepdNgDeM7yqW8hsRF2WO3CSiJuFKgVrGx2Kd0Ymb1NNiLphZ2JOnjhXDx01ykbPGA3KB0D8ZfcKgcICaWQ9fuplJi5bpmU3pVKMSPiw34JSP5EZ3lh075Tpg3TXvI50eAqPDVSpAxo5GsG2ueIjCqasKsfohMcBUUayScIFjNz3F3YjnTtiSGOi07LssLSEYfENK2jb1wbQ5DR6Ejl0g2PK0tdq3VtAHCg9HZxQb65jsbDv0GB12o2GkVvUtbXZL360dK8jqFx+Gjff";

    private static final float mmPerInch = 25.4f;

    // Constant for Stone Target
    private static final float stoneZ = 2.00f * mmPerInch;

    // Class Members
    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;
    private boolean targetVisible = false;
    public boolean leftBlock = false;
    public boolean rightBlock = false;
    public boolean theCenterBlock = false;
    private float phoneXRotate = 0;
    private float phoneYRotate = 0;
    private float phoneZRotate = 0;

    //Newly added Vuforia related Members for the simplified program
    private VuforiaLocalizer.Parameters parameters;

    private VuforiaTrackables targetsSkyStone;
    private VuforiaTrackable stoneTarget;

    private OpenGLMatrix robotFromCamera;
    private VuforiaTrackableDefaultListener listener;

    private float robotX = 0;
    private float robotY = 0;
    private float robotAngle = 0;

    private float targetX = 0;
    private float targetY = 0;
    private float targetFirstAngle = 0;
    private float targetSecondAngle = 0;
    private float targetThirdAngle = 0;

    @Override
    public void runOpMode() {

        initRobot(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Set up Vuforia
        setupVuforia();

        // TODO probably move this to end portion of setupVuforia() method
        // Set up last known location to the origin as we don't know where the robot is.
        // If we don't include this, it would be null, which would cause errors later on
        // Change 8 - Set in the order of XYZ
        //lastLocation = OpenGLMatrix
        //        .translation(0, 0, 0)
        //        .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, 0, 0, 0));
        lastLocation = OpenGLMatrix
                .translation(0, 0, 0)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, 0, 0));

        //TODO Try this out to turn on the Flash
        CameraDevice.getInstance().setFlashTorchMode(true);

        telemetry.addData("Status", "Initialized");
        telemetry.addData(">", "Press Play to start");
        telemetry.update();

        waitForStart();

        //set encoder
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extensionMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extensionMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // rightArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //rightArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //leftArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //leftArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Start tracking the targets
        targetsSkyStone.activate();

        while (opModeIsActive()) {
            getToPosition();
            scanSkyStone(2000);
            getSkyStone();
            goToPlate();
            pullFoudationIN();
            break;
        }

        //TODO turn off the Flash if it made ON above
        //CameraDevice.getInstance().setFlashTorchMode(false);
    }

    private void setupVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        //TODO - Don't pass cameraMonitorViewId to not drain out the battery
        parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CAMERA_CHOICE;

        //Change 1 - Set Extended Tracking to false to avoid inaccurate measurements when vuforia losses sight of the target
        parameters.useExtendedTracking = false;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Load the data sets for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");
        //vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        //Change 2 - To avoid confusion, look for only Stone Target(0 is the index for Stone asset).
        //We can expand this later on when we are ready to scan other assets.
        stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");

        // Change 3 - Setting rotations 90, 0, 0(instead of 90, 0, -90) as we are are using the
        // robot starting location as origin (instead of center of Red Alliance Station)
        //stoneTarget.setLocation(OpenGLMatrix
        //        .translation(0, 0, stoneZ)
        //        .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));
        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        // TODO Turn OFF phone's Auto-Screen-Rotation option
        // NOTE !!!!  It's very important that you turn OFF your phone's Auto-Screen-Rotation option.
        // Lock it into Portrait for these numbers to work.

        // We need to rotate the camera around it's long axis to bring the correct camera forward.
        if (CAMERA_CHOICE == BACK) {
            // Change 4 - Set phoneXRotate instead of phoneYRotate
            //phoneYRotate = -90;
            phoneXRotate = 90;
        } else {
            // Change 5 - Set phoneXRotate instead of phoneYRotate
            //phoneYRotate = 90;
            phoneXRotate = -90;
        }

        // Change 6, Set phoneYRotate instead for LandScape Mode
        // Rotate the phone vertical about the X axis if it's in portrait mode
        //if (PHONE_IS_PORTRAIT) {
        //    phoneXRotate = 90 ;
        //}
        // Rotate the phone vertical about the Y axis if it's in Landscape mode
        if (!PHONE_IS_PORTRAIT) {
            phoneYRotate = 90;
        }

        // TODO - IMPORTANT - Set these values correctly
        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        final float CAMERA_FORWARD_DISPLACEMENT = 6.5f * mmPerInch;   // eg: Camera is 4 Inches in front of robot center
        final float CAMERA_VERTICAL_DISPLACEMENT = 12.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT = 5.0f * mmPerInch;    // eg: Camera is ON the robot's center line

        // Set phone location on robot
        //Change 7 - Order YZX is changed to XYZ
        //robotFromCamera = OpenGLMatrix
        //        .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
        //        .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));
        robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, phoneXRotate, phoneYRotate, phoneZRotate));

        // Setup listener and inform it of phone information
        listener = (VuforiaTrackableDefaultListener) stoneTarget.getListener();
        listener.setPhoneInformation(robotFromCamera, parameters.cameraDirection);
    }

    public boolean scanSkyStone(double time) {

        long startTime = System.currentTimeMillis();

        telemetry.addData("Time1", startTime);
        telemetry.update();

        // TODO probably remove or reduce this Sleep
        // Sleeping for 1/2 second.
        sleep(500);

        while (System.currentTimeMillis() < startTime + time) {
            // Ask the listener for the latest information on where the robot is
            OpenGLMatrix latestLocation = listener.getUpdatedRobotLocation();

            // The listener will sometimes return null, so we check for that to prevent errors
            if (latestLocation != null)
                lastLocation = latestLocation;

            float[] coordinates = lastLocation.getTranslation().getData();
            float[] targetCoordinates = stoneTarget.getLocation().getTranslation().getData();

            //float xVal = lastLocation.getTranslation().get(0);
            //float yVal = lastLocation.getTranslation().get(1);

            robotX = coordinates[0];
            robotY = coordinates[1];
            robotAngle = Orientation.getOrientation(lastLocation, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;

            targetX = targetCoordinates[0];
            targetY = targetCoordinates[1];
            targetFirstAngle = Orientation.getOrientation(stoneTarget.getLocation(), EXTRINSIC, XYZ, DEGREES).firstAngle;
            targetSecondAngle = Orientation.getOrientation(stoneTarget.getLocation(), EXTRINSIC, XYZ, DEGREES).secondAngle;
            targetThirdAngle = Orientation.getOrientation(stoneTarget.getLocation(), EXTRINSIC, XYZ, DEGREES).thirdAngle;

            telemetry.addData("Scanning the", "SKY STONE");

            // Send information about whether the target is visible, and where the robot is
            telemetry.addData("Tracking " + stoneTarget.getName(), listener.isVisible());
            telemetry.addData("Last Known Location", formatMatrix(lastLocation));

            if (listener.isVisible()) {
                telemetry.addData("Target", "VISIBLE");
                telemetry.addData("robotX is ", robotX);
                telemetry.addData("robotY is ", robotY);
                telemetry.addData("targetX is ", targetX);
                telemetry.addData("targetY is ", targetX);
                telemetry.addData("targetFirstAngle is ", targetFirstAngle);
                telemetry.addData("targetSecondAngle is ", targetSecondAngle);
                telemetry.addData("targetThirdAngle is ", targetThirdAngle);

                //return true; TODO remove this comment
                if (robotX > 120) {
                    telemetry.addData("REACHING LEFT", "TARGET");
                    telemetry.update();
                    leftBlock = true;
                    theCenterBlock = false;
                    rightBlock = false;
                    return true;
                } else if (robotX < 120) {
                    telemetry.addData("REACHING _CENTER", "TARGET");
                    telemetry.update();
                    theCenterBlock = true;
                    leftBlock = false;
                    rightBlock = false;
                    return true;
                } else {
                    theCenterBlock = false;
                    leftBlock = false;
                    rightBlock = true;
                    telemetry.addData("Reaching_Right", "TARGET");
                    telemetry.update();

                }
                telemetry.update();

            }

        }
        return false;
    }

    // Formats a matrix into a readable stringprivate
    String formatMatrix(OpenGLMatrix matrix) {
        return matrix.formatAsTransform();
    }


    public void getSkyStone() {

        // TODO change the values that we are comparing as X,Y and Z are relative to origin( starting point in this case)


        if (theCenterBlock) {
            center();
        } else if (leftBlock) {
            left();
        } else {
            right();
        }

    }

    public void getToPosition() {
        long startTime = System.currentTimeMillis();
        releaseFoundation();
        while (System.currentTimeMillis() < startTime + 500) {
            armServo.setPower(0.5);
        }
        driveStraight(.5, 10.75);
    }

    public void getSkyStonePush() {
        strafeRight(2, 6);
        driveStraight(2, 4);
        rotate(2, -16);
        driveStraight(2, 16);
        rotate(2, -16);
        driveStraight(2, 16);
    }

    public void left() {

        strafeLeft(2, 7);
        driveStraight(.75, 13.25);
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() < startTime + 1500) {
            extentionServo.setPower(-.75);
        }
        extentionServo.setPower(0);
        long startTime2 = System.currentTimeMillis();
        while (System.currentTimeMillis() < startTime2 + 500) {
            armServo.setPower(-.5);
        }
        driveStraight(2, -6.75);

    }

    public void right() {
        strafeRight(.5, 7.5);
        driveStraight(.5, 11);
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() < startTime + 1500) {
            extentionServo.setPower(-.75);
        }
        extentionServo.setPower(0);
        long startTime2 = System.currentTimeMillis();
        while (System.currentTimeMillis() < startTime2 + 500) {
            armServo.setPower(-.5);
        }
        driveStraight(1, -6.25);
    }

    public void center() {
        //raiseArm(-1,1);
        driveStraight(.75, 11.5);
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() < startTime + 1500) {
            extentionServo.setPower(-.75);
        }
        extentionServo.setPower(0);
        long startTime2 = System.currentTimeMillis();
        while (System.currentTimeMillis() < startTime2 + 1000) {
            armServo.setPower(-.5);


        }
        armServo.setPower(-.5);
        driveStraight(2, -6.25);
    }

    public void goToPlate() {
        foundationServo1.setPosition(1);
        foundationServo2.setPosition(1);
        rotate(.5, -14.1);
        if (theCenterBlock) {
            driveStraight(1, 67);
        }
        else if (leftBlock){
            driveStraight(1,61);
        }
        else{
           driveStraight(1,74.6);
        }
            long startTime=System.currentTimeMillis();
        while (startTime+100<System.currentTimeMillis()){
            armServo.setPower(.5);
        }


            rotate(.75, 14.1);
            //extensionMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

           // extensionMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //int extensionMotorPosition = extensionMotor.getCurrentPosition();
            //telemetry.addData("extention position", extensionMotorPosition);
           // extensionMotor.setTargetPosition(-2000);
            //extensionMotor.setPower(1);
            //while (extensionMotor.getCurrentPosition()>-1990) {
             //   telemetry.addData("motor is", extensionMotor.getCurrentPosition());
            //armServo.setPower(-.5);
            // telemetry.update();
            //}
            long extentionTime = System.currentTimeMillis();
        while (System.currentTimeMillis() < extentionTime + 200) {
            extensionMotor.setPower(-2);
        }

            driveStraight(.75, 10);
            extensionMotor.setPower(0);
            long startTime2 = System.currentTimeMillis();
            while (System.currentTimeMillis() < startTime2 + 1400) {
                extentionServo.setPower(-.75);
                armServo.setPower(-.5);

            }
            extentionServo.setPower(0);

            // extensionMotor.setTargetPosition(-500);
            // extensionMotor.setPower(1);
            // while (extensionMotor.getCurrentPosition()<-490) {
            //   telemetry.addData("motor is", extensionMotor.getCurrentPosition());
            // armServo.setPower(-.5);
            //telemetry.update();
            //}
           long extentionTime2 = System.currentTimeMillis();
            while (System.currentTimeMillis() < extentionTime2 + 450) {
                extensionMotor.setPower(2);
            }
            extensionMotor.setPower(0);
            long startTime3 = System.currentTimeMillis();
            while (System.currentTimeMillis() < startTime3 + 500) {
                armServo.setPower(.5);
            }
            long extentionTime3 = System.currentTimeMillis();
            while (System.currentTimeMillis() < extentionTime3 + 200) {
                extensionMotor.setPower(-2);
            }
            extensionMotor.setPower(0);

    }




    public void driveStraightclamp(double power, double inches) {
        resetDriveMotors();
        setDriveMotorsRunToPosition();
        int ticks = (int) (TICKS_PER_INCH * -inches);

        frontLeftMotor.setTargetPosition(ticks);     //tells how far (based off of ticks) the robot should go
        frontRightMotor.setTargetPosition(ticks);
        backLeftMotor.setTargetPosition(ticks);
        backRightMotor.setTargetPosition(ticks);

        double drivePower = Range.clip(power, 0, 1);

        // move
        frontLeftMotor.setPower(-drivePower);
        frontRightMotor.setPower(drivePower);
        backLeftMotor.setPower(drivePower);
        backRightMotor.setPower(-drivePower);

        // keep looping until motor reach to the target position
        while (frontLeftMotor.isBusy() && opModeIsActive()) {
            loopCount++;


            telemetry.addData("Loop is ", loopCount);
            telemetry.addData("Tgt is ", ticks);
            telemetry.addData("Left is ", frontLeftMotor.getCurrentPosition());
            telemetry.addData("Right is ", frontRightMotor.getCurrentPosition());
            telemetry.addData("Power is ", drivePower);
            telemetry.update();
        }

        frontLeftMotor.setPower(0.0);
        frontRightMotor.setPower(0.0);
    }
    public void pushFoundationIn(){


        sleep(200);

        //grabbing foundation using servo

        //move forward to hit foundation
        driveStraight(0.25, 1.5 );

        //grab foundation
        grabFoundation();

        sleep(1000);

        driveStraight(.5,-6);

        //rotate to put foundation in base
        rotate(.5,-12);

        //move back 22 inches
        driveStraight(0.25, -15 );

        //rotate to put foundation in base
        rotate(1,-16);

        releaseFoundation();

        //align foundation to the wall
        driveStraight(0.25, 24 );


        driveStraight(1,-20);
        long startTime=System.currentTimeMillis();
        while (startTime+500>System.currentTimeMillis()) {
            extensionMotor.setPower(2);
        }
        extensionMotor.setPower(0);
        driveStraight(1,-12);
    }
    public void pullFoudationIN(){
        driveStraight(.5,3);
        foundationServo1.setPosition(.27);
        foundationServo2.setPosition(.31);
        sleep(1000);
        driveStraight(.1,-37);
        foundationServo1.setPosition(.8);
        foundationServo2.setPosition(.83);
        sleep(500);
        strafeRight(.5,22);
        driveStraight(.5,14);
        strafeRight(.5,9);
    }

}