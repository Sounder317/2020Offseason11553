package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import java.lang.annotation.Target;
        import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
        import com.qualcomm.robotcore.hardware.DigitalChannel;
        import com.qualcomm.robotcore.hardware.DistanceSensor;
        import com.qualcomm.robotcore.hardware.Servo;
        import com.qualcomm.robotcore.util.Range;
        import org.firstinspires.ftc.robotcore.external.navigation.Position;
        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.hardware.HardwareMap;
        import com.qualcomm.robotcore.hardware.Gyroscope;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.ColorSensor;
        import com.qualcomm.robotcore.hardware.CRServo;
        import android.app.Activity;
        import android.graphics.Color;
        import android.view.View;

@TeleOp
public class R2_Skystone1_TeleOp extends R_2_OpMode {

    //@Override
    public void runOpMode() {
        initRobot(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (!opModeIsActive()) {
            telemetry.addData("Status", "Initialized");
            telemetry.update();
            waitForStart();
        }

        double motorPower;
        double steering;
        boolean foundationMove = false;
        double leftPower;
        double rightPower;

        while (opModeIsActive()) {

            setDriveMotorsMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            setArmMotorsMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            extensionMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            loopCount++;

            // right stick is throttle
            motorPower = -this.gamepad1.left_stick_y;

            // right stick steering
            steering = this.gamepad1.left_stick_x;



            // drive formula
            leftPower = motorPower + steering;
            rightPower = motorPower - steering;

            // if triggers are held go slower
            if (this.gamepad1.left_trigger > 0){
                rightPower = rightPower / 5.0;
                leftPower = leftPower / 5.0;
            }
            else if (this.gamepad1.right_trigger > 0) {
                rightPower = rightPower / 2.0;
                leftPower = leftPower / 2.0;
            }

            if (this.gamepad2.y) {
                loadBucket();
            } else if (this.gamepad2.x) {
                unloadBucket();
            } else {
                stopServo();
            }

            // make sure we dont make the power too low/high
            leftPower = Range.clip(leftPower, -1, .75);
            rightPower = Range.clip(rightPower, -1, .75);

            frontLeftMotor.setPower(-rightPower);
            backLeftMotor.setPower(-rightPower);

            frontRightMotor.setPower(-leftPower);
            backRightMotor.setPower(-leftPower);


        /*    frontLeftMotor.setPower(steering);      //allows robot to strafe
            frontRightMotor.setPower(-steering);
            backLeftMotor.setPower(-steering);
            backRightMotor.setPower(steering);  */

            if (this.gamepad1.left_bumper)             //other strafing method
            {
                motorPower = 1.0;

                frontLeftMotor.setPower(motorPower);
                frontRightMotor.setPower(-motorPower);
                backLeftMotor.setPower(-motorPower);
                backRightMotor.setPower(motorPower);
            }
            else if (this.gamepad1.right_bumper)
            {
                motorPower = 1.0;

                frontLeftMotor.setPower(-motorPower);
                frontRightMotor.setPower(motorPower);
                backLeftMotor.setPower(motorPower);
                backRightMotor.setPower(-motorPower);
            }

            if (this.gamepad2.dpad_up) {
                extensionMotor.setPower(-1.0);
            } else if (this.gamepad2.dpad_down) {
                extensionMotor.setPower(0.75);
            } else {
                extensionMotor.setPower(0);
            }

            if (gamepad2.left_bumper){
                extentionServo.setPower(0.5);
            }
            else if (gamepad2.right_bumper){
                extentionServo.setPower(-0.5);
            }
            else {
                extentionServo.setPower(0);
            }

            if (this.gamepad1.b){
                foundationServo1.setPosition(.3);
                foundationServo2.setDirection(Servo.Direction.REVERSE);
                foundationServo2.setPosition(.3);
            }

            if (this.gamepad1.y) {
                foundationServo1.setPosition(1);
                foundationServo2.setDirection(Servo.Direction.REVERSE);
                foundationServo2.setPosition(1);
            }

            telemetry.addData("Left Front is ", frontLeftMotor.getCurrentPosition());
            telemetry.addData("Right Front is ", frontRightMotor.getCurrentPosition());
            telemetry.addData("Loop Count", loopCount);
            telemetry.update();
        }
    }
}

