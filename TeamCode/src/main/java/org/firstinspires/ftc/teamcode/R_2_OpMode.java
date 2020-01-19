package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
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
   // protected DcMotor leftArmMotor;
    //protected DcMotor rightArmMotor;
    protected DcMotor extensionMotor;

    protected CRServo armServo;
    protected CRServo extentionServo;
    protected Servo push1Servo;


    protected Servo foundationServo1;
    protected Servo foundationServo2;
    protected Servo push2Servo;
    protected Servo swivelServo;

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

      //  leftArmMotor = hardwareMap.get(DcMotor.class, "leftArmMotor");
       // rightArmMotor = hardwareMap.get(DcMotor.class, "rightArmMotor");

        extensionMotor = hardwareMap.get(DcMotor.class, "extensionMotor");

        armServo = hardwareMap.get(CRServo.class, "armServo");
        extentionServo = hardwareMap.get(CRServo.class, "extensionServo");
        push1Servo = hardwareMap.get(Servo.class, "push1Servo");

        foundationServo1 = hardwareMap.get(Servo.class, "foundationServo1");
        foundationServo2 = hardwareMap.get(Servo.class, "foundationServo2");
        swivelServo= hardwareMap.get(Servo.class,"swivelServo");
        foundationServo2.setDirection(Servo.Direction.REVERSE);

        push2Servo= hardwareMap.get(Servo.class, "push2Servo");

        setDriveMotorsMode(runMode);

        // set the direction of the right motors so they match the left motors
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //leftArmMotor.setPower(0.0);
        //rightArmMotor.setPower(0.0);

        resetDriveMotors();
        //resetArmMotors();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    public void driveStraight(double power, double inches)
    {
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
    public void raiseArm(double power, double rotations)
    {
        extensionMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int ticks = (int) (TICKS_PER_ROTATION*rotations);

        extensionMotor.setTargetPosition(ticks);
        extensionMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        double extentionPower=Range.clip(power,0, 1);

        extensionMotor.setPower(extentionPower);

        while(extensionMotor.isBusy() && opModeIsActive()){
            telemetry.addData("Extension is", extensionMotor.getCurrentPosition());
            telemetry.update();
        }
    }

    public void drop(){
        push2Servo.getPosition();
        push1Servo.getPosition();
        push1Servo.setPosition(1);
        push2Servo.setPosition(1);
    }

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

 //   public void setArmMotorsMode(DcMotor.RunMode runMode)
   // {
     //   leftArmMotor.setMode(runMode);
       // rightArmMotor.setMode(runMode);
   // }

  //  public void resetArmMotors()
    //{
      //  leftArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //rightArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    //}

    public void loadBucket()
    {
       armServo.setPower(0.5);
    }

    public void unloadBucket()
    {
      armServo.setPower(-0.5);
    }

    public void stopServo(){armServo.setPower(0);}

    public void extendArm() {extentionServo.setPower(1);}

    public void retractArm() {extentionServo.setPower(-1);}

    public void stopExtension() {extentionServo.setPower(0);}

    public void grabFoundation() {
        foundationServo1.setPosition(.35);
        foundationServo2.setPosition(.21);
    }

    public void releaseFoundation() {
        foundationServo1.setPosition(1);
        foundationServo2.setPosition(.83);
    }

    public void semiReleaseFoundation()  {
        foundationServo1.setPosition(-0.50);
    }

    public void scanSkyStones()
    {

    }
}
