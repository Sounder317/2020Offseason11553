package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.ColorSensor;

@Disabled
public abstract class R_2_OpMode extends LinearOpMode
{
    // sensors
    protected Gyroscope imu;

    // motors
    protected DcMotor frontLeftMotor;
    protected DcMotor frontRightMotor;
    protected DcMotor backLeftMotor;
    protected DcMotor backRightMotor;
    protected DcMotor leftArmMotor;
    protected DcMotor rightArmMotor;
    protected DcMotor extensionMotor;

     protected CRServo armServo;
     protected CRServo extentionSevro;

    protected int loopCount = 0;                // counter for how long opMode has run
    protected boolean slowMode = false;         // whether to be in slow or precise mode
    protected boolean lol = false;
    // NeverRest40 is 1120 and the ratio is 1.75
    protected static final int TICKS_PER_ROTATION = 640;

    // 4" wheels
    protected static final int TICKS_PER_INCH = 51;

    public void initRobot(DcMotor.RunMode runMode)
    {
        imu = hardwareMap.get(Gyroscope.class, "imu");

        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");

        leftArmMotor = hardwareMap.get(DcMotor.class, "leftArmMotor");
        rightArmMotor = hardwareMap.get(DcMotor.class, "rightArmMotor");

        extensionMotor = hardwareMap.get(DcMotor.class, "extensionMotor");

           armServo = hardwareMap.get(CRServo.class, "armServo");
           extentionSevro = hardwareMap.get(CRServo.class, "extensionServo");

        setDriveMotorsMode(runMode);
        //    setArmMotorsMode(runMode);

        // set the direction of the right motors so they match the left motors
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);

        //    rightArmMotor.setDirection(DcMotor.Direction.REVERSE);

        //  leftArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //rightArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftArmMotor.setPower(0.0);
        rightArmMotor.setPower(0.0);

        resetDriveMotors();
        resetArmMotors();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    public void driveStraight(double power, double inches)
    {
        resetDriveMotors();
        setDriveMotorsRunToPosition();

        int ticks = (int) (TICKS_PER_INCH * -inches);

        frontLeftMotor.setTargetPosition(ticks);
        frontRightMotor.setTargetPosition(ticks);
        backLeftMotor.setTargetPosition(ticks);
        backRightMotor.setTargetPosition(ticks);

        double drivePower = Range.clip(power, 0, 1);

        // move
        frontLeftMotor.setPower(drivePower);
        frontRightMotor.setPower(-drivePower);
        backLeftMotor.setPower(drivePower);
        backRightMotor.setPower(-drivePower);

        // keep looping until motor reach to the target position
        while (frontLeftMotor.isBusy() && opModeIsActive())
        {
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
        backLeftMotor.setPower(0.0);
        backRightMotor.setPower(0.0);

        setDriveMotorsMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void rotate(double power, double inches)
    {
        resetDriveMotors();
        setDriveMotorsMode(DcMotor.RunMode.RUN_TO_POSITION);

        int ticks = (int) (TICKS_PER_INCH * -(inches * 1.4));

        frontLeftMotor.setTargetPosition(-ticks);
        frontRightMotor.setTargetPosition(ticks);
        backLeftMotor.setTargetPosition(-ticks);
        backRightMotor.setTargetPosition(ticks);

        double drivePower = Range.clip(power, 0, 1);

        // move
        frontLeftMotor.setPower(drivePower);
        frontRightMotor.setPower(-drivePower);
        backLeftMotor.setPower(drivePower);
        backRightMotor.setPower(-drivePower);

        // keep looping until motor reach to the target position
        while(frontLeftMotor.isBusy() && opModeIsActive())
        {
            telemetry.addData("Left is ", frontLeftMotor.getCurrentPosition());
            telemetry.addData("Right is ", frontRightMotor.getCurrentPosition());
            telemetry.addData("Power is ", drivePower);
            telemetry.update();
        }
    }

    public void strafeRight(double power, double inches)
    {
        resetDriveMotors();
        setDriveMotorsMode(DcMotor.RunMode.RUN_TO_POSITION);

        int ticks = (int) (TICKS_PER_INCH * -(inches * 1.4));

        frontLeftMotor.setTargetPosition(ticks);
        frontRightMotor.setTargetPosition(-ticks);
        backLeftMotor.setTargetPosition(-ticks);
        backRightMotor.setTargetPosition(ticks);

        double drivePower = Range.clip(power, 0, 1);

        // move
        frontLeftMotor.setPower(drivePower);
        frontRightMotor.setPower(-drivePower);
        backLeftMotor.setPower(drivePower);
        backRightMotor.setPower(-drivePower);

        // keep looping until motor reach to the target position
        while(frontLeftMotor.isBusy() && opModeIsActive())
        {
            telemetry.addData("Left is ", frontLeftMotor.getCurrentPosition());
            telemetry.addData("Right is ", frontRightMotor.getCurrentPosition());
            telemetry.addData("Power is ", drivePower);
            telemetry.update();
        }
    }

    public void strafeLeft(double power, double inches)
    {
        resetDriveMotors();
        setDriveMotorsMode(DcMotor.RunMode.RUN_TO_POSITION);

        int ticks = (int) (TICKS_PER_INCH * -(inches * 1.4));

        frontLeftMotor.setTargetPosition(-ticks);
        frontRightMotor.setTargetPosition(ticks);
        backLeftMotor.setTargetPosition(ticks);
        backRightMotor.setTargetPosition(-ticks);

        double drivePower = Range.clip(power, 0, 1);

        // move
        frontLeftMotor.setPower(drivePower);
        frontRightMotor.setPower(-drivePower);
        backLeftMotor.setPower(drivePower);
        backRightMotor.setPower(-drivePower);

        // keep looping until motor reach to the target position
        while(frontLeftMotor.isBusy() && opModeIsActive())
        {
            telemetry.addData("Left is ", frontLeftMotor.getCurrentPosition());
            telemetry.addData("Right is ", frontRightMotor.getCurrentPosition());
            telemetry.update();
        }
    }

    //public void raiseArm()
//    {
    // save the mode
    //  DcMotor.RunMode leftMode = leftArmMotor.getMode();
    //      DcMotor.RunMode rightMode = rightArmMotor.getMode();

    //    leftArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    //  leftArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    //rightArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    //    rightArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    //    leftArmMotor.setTargetPosition(3000);
    //  rightArmMotor.setTargetPosition(3000);

    //    leftArmMotor.setPower(0.5);
    //  rightArmMotor.setPower(0.5);

    // keep looping until motor reach to the target position
    //while(leftArmMotor.isBusy() && rightArmMotor.isBusy())
    //{
    //  telemetry.addData("Left Arm is ", leftArmMotor.getCurrentPosition());
    //    telemetry.addData("Right Arm is ", rightArmMotor.getCurrentPosition());
    //  telemetry.update();
    //}

    //leftArmMotor.setPower(0.0);
//        rightArmMotor.setPower(0.0);
//
    //      leftArmMotor.setMode(leftMode);
    //    rightArmMotor.setMode(rightMode);
    //}

//    public void lowerArm()
    //  {
    //    // save the mode
    //  DcMotor.RunMode leftMode = leftArmMotor.getMode();
    //    DcMotor.RunMode rightMode = rightArmMotor.getMode();

    //     leftArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    //   rightArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        leftArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    //      rightArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
    //      leftArmMotor.setTargetPosition(0);
    //    rightArmMotor.setTargetPosition(0);
//
    //      leftArmMotor.setPower(-0.5);
    //    rightArmMotor.setPower(-0.5);
    //  backRightMotor.setPower(-0.2);
    //backLeftMotor.setPower(-0.2);
//
    //      // keep looping until motor reach to the target position
    //    while(leftArmMotor.isBusy() && rightArmMotor.isBusy())
    //  {
    //    telemetry.addData("Left Arm is ", leftArmMotor.getCurrentPosition());
//            telemetry.addData("Right Arm is ", rightArmMotor.getCurrentPosition());
    //          telemetry.update();
    //    }
    //  leftArmMotor.setPower(0.0);
    //rightArmMotor.setPower(0.0);

    //    resetArmMotors();
    //}

    public void setDriveMotorsMode(DcMotor.RunMode runMode)
    {
        frontLeftMotor.setMode(runMode);
        frontRightMotor.setMode(runMode);
        backLeftMotor.setMode(runMode);
        backRightMotor.setMode(runMode);
    }

    public void setDriveMotorsRunToPosition()
    {
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void resetDriveMotors()
    {
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void setArmMotorsMode(DcMotor.RunMode runMode)
    {
        leftArmMotor.setMode(runMode);
        rightArmMotor.setMode(runMode);
    }

    public void resetArmMotors()
    {
        leftArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

     public void loadBucket()
    {
       armServo.setPower(0.5);
    }

    public void unloadBucket()
    {
      armServo.setPower(-0.5);
    }

    public void stopServo(){armServo.setPower(0);}

    public void extendArm() {extentionSevro.setPower(1);}

    public void retractArm() {extentionSevro.setPower(-1);}

    public void stopExtension() {extentionSevro.setPower(0);}

    public void scanSkyStones()
    {

    }
}
