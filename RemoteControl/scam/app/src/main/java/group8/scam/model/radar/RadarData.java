package group8.scam.model.radar;

/**
 * This class handles the Data relating to the Radar such as the angle of the servo, the readings
 * from the ultrasonic sensor, etc
 * Created by @Firas
 */

public class RadarData {

    private int angleOfServo = 0;
    private int ultrasonicReading;
    private int minAngle = 0;
    private int maxAngle = 0;
    private String dataString;
    private boolean endOfString = true;

    public RadarData(int angleOfServo) {
        this.angleOfServo = angleOfServo;
    }

    /**
     * This method is provided with the new Angle of the Servo
     * and stores it locally before sending it over to the Radar
     * to update the position of the pulse
     *
     * @param newAngle - the new angle the Servo is currently at
     */
    private void setAngle(int newAngle) {
        this.angleOfServo = newAngle;
    }

    /**
     * This method retrieves the String sent by the car to the Handler and
     * processes the string by verifying whether a character is a number/digit
     * or not. This ensures that any text indicators and unwanted data are filtered
     * and only the distance reading remains.
     * <p>
     * Furthermore, the string used to retrieve the distance reading is emptied
     * after filtering to ensure future calls of the method are correct.
     *
     * @param servoData - Raw data received as a string from the Handler
     * @see #filterDataIntoDigit(String)
     */
    private void filterServoAngle(String servoData) {
        for (int i = 0; i < servoData.length(); i++) {
            if (Character.isDigit(servoData.charAt(i))) {
                dataString += servoData.charAt(i);
            }
        }
        setAngle(Integer.parseInt(dataString));
    }

    /**
     * This method retrieves the current Servo Angle and returns it.
     *
     * @return int angleOfServo
     */
    private int getAngleOfServo() {
        return this.angleOfServo;
    }

    /**
     * This method retrieves the String sent by the car to the Handler and
     * processes the string by verifying whether a character is a number/digit
     * or not. This ensures that any text indicators and unwanted data are filtered
     * and only the distance reading remains.
     * <p>
     * Furthermore, the string used to retrieve the distance reading is emptied
     * after filtering to ensure future calls of the method are correct.
     *
     * @param servoData - Raw data received as a string from the Handler
     * @return the distance reading from the ultrasonic sensor
     * @see #filterDataIntoDigit(String)
     */
    private int filterUltrasonicReading(String servoData) {
        this.ultrasonicReading = Integer.parseInt(filterDataIntoDigit(servoData));
        return getUltrasonicReading();
    }

    /**
     * This method retrieves the local ultrasonic distance reading and returns it.
     *
     * @return local ultrasonic distance reading
     */
    private int getUltrasonicReading() {
        return this.ultrasonicReading;
    }

    /**
     * This method retrieves the String sent by the car to the Handler and
     * processes the string by verifying whether a character is a number/digit
     * or not and going through the String separators.
     * This ensures that any text indicators and unwanted data are filtered
     * and only the distance reading remains.
     * <p>
     * Furthermore, the string used to retrieve the data is emptied
     * after filtering to ensure future calls of the method are correct.
     *
     * @param data - Raw data received as a string from the Handler
     */
    private void filterDataIntoDigit(String data) {
        for (int i = 0; i < data.length(); i++) {
            if (Character.isLetter(data.charAt(i))) {
                if (data.charAt(i) == 'a') {
                    filterServoAngle(data.substring(i, data.length()));
                }
                else if (data.charAt(i) == 'u') {
                    filterUltrasonicReading(data.substring(i, data.length()));
                }
            }
        }
    }

}
