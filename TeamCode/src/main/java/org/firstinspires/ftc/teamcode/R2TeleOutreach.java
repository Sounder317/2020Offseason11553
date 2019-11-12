package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class R2TeleOutreach extends R_2_OpMode {
    //@Override
    public void runOpMode() {
        initRobot(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (!opModeIsActive()) {
            telemetry.addData("Status", "Initialized");
            telemetry.update();
            waitForStart();
        }

        double motorPower = 0;
        double steering = 0;

        double leftPower = -0;
        double rightPower = 0;

        while (opModeIsActive()) {
            setDriveMotorsMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            setArmMotorsMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            extensionMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            // make sure color sensor servos not open
            loopCount++;
            if (lol=false) {
                // right stick is throttle
                motorPower = this.gamepad1.left_stick_y;

                // right stick steering
                steering = this.gamepad1.left_stick_x;

                // drive formula
                leftPower = motorPower + steering;
                rightPower = motorPower - steering;

                //if the a button is pressed then toggle if you are in slowMode
                if (this.gamepad1.a) {
                    slowMode = !slowMode;
                }

                if (this.gamepad1.right_stick_y > 0.1) {
                    motorPower = this.gamepad1.right_stick_y;
                    steering = this.gamepad2.right_stick_x;
                    leftPower = motorPower - steering;
                    rightPower = motorPower - steering;
                    telemetry.addLine("Strafe on");
                } else if (this.gamepad1.right_stick_y < -0.1) {
                    motorPower = this.gamepad1.right_stick_y;
                    steering = this.gamepad2.right_stick_x;
                    leftPower = motorPower - steering;
                    rightPower = motorPower - steering;
                } else if (this.gamepad1.right_stick_x > 0.1) {
                    motorPower = this.gamepad1.right_stick_y;
                    steering = this.gamepad2.right_stick_x;
                    leftPower = motorPower - steering;
                    rightPower = motorPower - steering;
                    telemetry.addLine("Strafe on");
                } else if (this.gamepad1.right_stick_x < -0.1) {
                    motorPower = this.gamepad1.right_stick_y;
                    steering = this.gamepad2.right_stick_x;
                    leftPower = motorPower - steering;
                    rightPower = motorPower - steering;
                    telemetry.addLine("Strafe on");
                } else {
                    telemetry.addLine("Strafe off");
                }
//            if (this.gamepad1.b)
//            {
//                //raiseArm();
//                driveStraight(0.5,10.0);
//            }

                if (slowMode) {
                    leftPower = leftPower / 2.0;
                    rightPower = rightPower / 2.0;
                }

                //armpower
                // Move arm up and down
                if (this.gamepad1.left_bumper) {
                    // move arm up
                    leftArmMotor.setPower(-2);
                    rightArmMotor.setPower(2);
                } else if (this.gamepad1.right_bumper) {
                    // move arm down
                    rightArmMotor.setPower(-2);
                    leftArmMotor.setPower(2);
                } //else if (this.gamepad2.left_bumper) {
                // move arm up
                //  leftArmMotor.setPower(2);
                //   rightArmMotor.setPower(-2);
                // } //else if (this.gamepad2.right_bumper) {
                //  move arm down
                //  rightArmMotor.setPower(2);
                //leftArmMotor.setPower(-2);
                // }
                else {
                    rightArmMotor.setPower(0.0);
                    leftArmMotor.setPower(0.0);
                }

                if (this.gamepad2.right_trigger > 0) {
                    loadBucket();
                } else if (this.gamepad2.left_trigger > 0) {
                    unloadBucket();
                } else {
                    stopServo();
                }

                // make sure we dont make the power too low/high
                leftPower = Range.clip(leftPower, -1, 1);
                rightPower = Range.clip(rightPower, -1, 1);

                frontLeftMotor.setPower(leftPower);
                backLeftMotor.setPower(rightPower);

                frontRightMotor.setPower(rightPower);
                backRightMotor.setPower(leftPower);

                if (this.gamepad1.dpad_left) {
                    motorPower = 1.0;

                    frontLeftMotor.setPower(-motorPower);
                    frontRightMotor.setPower(motorPower);
                    backLeftMotor.setPower(-motorPower);
                    backRightMotor.setPower(motorPower);
                } else if (this.gamepad1.dpad_right) {
                    motorPower = 1.0;

                    frontLeftMotor.setPower(motorPower);
                    frontRightMotor.setPower(-motorPower);
                    backLeftMotor.setPower(motorPower);
                    backRightMotor.setPower(-motorPower);
                }
                if (this.gamepad2.dpad_up) {
                    extensionMotor.setPower(0.5);
                } else if (this.gamepad2.dpad_down) {
                    extensionMotor.setPower(-0.5);
                } else {
                    extensionMotor.setPower(0);
                }
                if (this.gamepad2.right_bumper) {
                    extendArm();
                } else if (this.gamepad2.left_bumper) {
                    retractArm();
                } else {
                    stopExtension();
                }
            }
            if (lol){
                // right stick is throttle
                motorPower = this.gamepad2.left_stick_y;

                // right stick steering
                steering = this.gamepad2.left_stick_x;

                // drive formula
                leftPower = motorPower + steering;
                rightPower = motorPower - steering;

                //if the a button is pressed then toggle if you are in slowMode
                if (this.gamepad2.a) {
                    slowMode = !slowMode;
                }

            }
                //    telemetry.addData("Left Arm is ", leftArmMotor.getCurrentPosition());
                //  telemetry.addData("Right Arm is ", rightArmMotor.getCurrentPosition());

                telemetry.addData("Left Front is ", frontLeftMotor.getCurrentPosition());
                telemetry.addData("Right Front is ", frontRightMotor.getCurrentPosition());

                telemetry.addData("Arm Servo", armServo.getPower());
                telemetry.addData("Slow Mode", slowMode);
                telemetry.addData("Loop Count", loopCount);
                telemetry.update();
            }
        }
    }
