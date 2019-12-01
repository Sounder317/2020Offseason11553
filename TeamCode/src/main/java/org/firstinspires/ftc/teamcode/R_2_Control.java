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
public class R_2_Control extends R_2_OpMode
{
    //@Override
    public void runOpMode()
    {

        initRobot(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (!opModeIsActive())
        {
            telemetry.addData("Status", "Initialized");
            telemetry.update();
            waitForStart();
        }

        double motorPower = 0;
        double steering = 0;

        double leftPower = -0;
        double rightPower = 0;

        while (opModeIsActive())
        {
            setDriveMotorsMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            setArmMotorsMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            //        extensionMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            // make sure color sensor servos not open
            loopCount++;

            // right stick is throttle
            motorPower = this.gamepad1.left_stick_y;

            // right stick steering
            steering = this.gamepad1.left_stick_x;

            // drive formula
            leftPower = motorPower - steering;
            rightPower = motorPower + steering;

            //if the a button is pressed then toggle if you are in slowMode
            if (this.gamepad1.a)
            {
                slowMode = !slowMode;
            }
//            if (this.gamepad1.b)
//            {
//                //raiseArm();
//                driveStraight(0.5,10.0);
//            }

            if (slowMode)
            {
                leftPower = leftPower/2.0;
                rightPower = rightPower/2.0;
            }

            //armpower
            // Move arm up and down
            if (this.gamepad1.left_bumper)
            {
                // move arm up
                extentionServo.setPower(1);
            }
            else if (this.gamepad1.right_bumper)
            {
                // move arm down
               extentionServo.setPower((-1));
            }
            else {
               extentionServo.setPower(0);
            }
            if (this.gamepad2.left_bumper)
            {
                // move arm up
                leftArmMotor.setPower(1);
                rightArmMotor.setPower(-1);
            }
            else if (this.gamepad2.right_bumper)
            {
                //  move arm down
                rightArmMotor.setPower(1);
                leftArmMotor.setPower(-1);
            }
            else
            {
                rightArmMotor.setPower(0.0);
                leftArmMotor.setPower(0.0);
            }
            if(this.gamepad1.right_stick_button)
            {
                drop();
            }
            else {

                push1Servo.setPosition(push1Servo.getPosition());
                push2Servo.setPosition(push2Servo.getPosition());

            }
                 if (this.gamepad2.y)
            {
                    loadBucket();
             }

               else if (this.gamepad2.x)
            {
                  unloadBucket();
                }
              else
            {
             stopServo();
                }

            // make sure we dont make the power too low/high
            leftPower = Range.clip(leftPower, -1, 1);
            rightPower = Range.clip(rightPower, -1, 1);

            frontLeftMotor.setPower(leftPower);
            backLeftMotor.setPower(rightPower);

            frontRightMotor.setPower(rightPower);
            backRightMotor.setPower(leftPower);

            if (this.gamepad1.dpad_left)
            {
                motorPower = 1.0;

                frontLeftMotor.setPower(motorPower);
                frontRightMotor.setPower(-motorPower);
                backLeftMotor.setPower(-motorPower);
                backRightMotor.setPower(motorPower);
            }
            else if (this.gamepad1.dpad_right)
            {
                motorPower = 1.0;

                frontLeftMotor.setPower(-motorPower);
                frontRightMotor.setPower(motorPower);
                backLeftMotor.setPower(motorPower);
                backRightMotor.setPower(-motorPower);
            }
              if (this.gamepad2.dpad_up)
            {
                extensionMotor.setPower(-0.5);
            }
            else if (this.gamepad2.dpad_down)
            {
                extensionMotor.setPower(0.5);
            }
            else
            {
              extensionMotor.setPower(0);
                }
            if (this.gamepad2.dpad_right){
                extentionServo.setPower(2);
            }
            else if(this.gamepad2.dpad_left){
                extentionServo.setPower(-2);
            }
            else {
                stopExtension();
            }
                telemetry.addData("Left Arm is ", leftArmMotor.getCurrentPosition());
              telemetry.addData("Right Arm is ", rightArmMotor.getCurrentPosition());

            telemetry.addData("Left Front is ", frontLeftMotor.getCurrentPosition());
            telemetry.addData("Right Front is ", frontRightMotor.getCurrentPosition());

            //telemetry.addData("Arm Servo", armServo.getPower());
            telemetry.addData("Slow Mode", slowMode);
            telemetry.addData("Loop Count", loopCount);
            telemetry.update();
        }
    }
}