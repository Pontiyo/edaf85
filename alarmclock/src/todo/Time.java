package todo;

import se.lth.cs.realtime.semaphore.MutexSem;
import se.lth.cs.realtime.semaphore.Semaphore;
import done.ClockInput;
import done.ClockOutput;

public class Time {

	private static ClockInput input;
	private static ClockOutput output;
	private Semaphore sem;
	private MutexSem mutex;
	public boolean alarm;
	public int timeH;
	public int timeM;
	public int timeS;
	public int alarmTime;
	public int alarmTimeLeft;

	public Time(ClockInput i, ClockOutput o, Semaphore sem, MutexSem mutex) {
		input = i;
		output = o;
		this.sem = sem;
		timeH = 8; // Kanske �ndra till system time?
		timeM = 8; 
		timeS = 8; 
		alarmTime = 4;
		alarmTimeLeft = 4;
		alarm = false;
		this.mutex = mutex;
	}


	public void checkButtons() {
		// TODO Auto-generated method stub
		sem.take(); // Kontroll f�r knapparna
		switch (input.getChoice()) {
		case ClockInput.SET_ALARM:
			mutex.take();
			alarmTime = input.getValue();
			mutex.give();

			break;
		case ClockInput.SET_TIME:
			mutex.take();
			int temp = input.getValue();
			timeH = temp / 10000;
			timeM = temp / 100 % 100;
			timeS = temp % 100;
			mutex.give();
			break;
		default:
			mutex.take();
			if (input.getAlarmFlag() != alarm) {
				alarm = input.getAlarmFlag();
				System.out.println("Alarm is " + (alarm ? "on" : "off"));
			}
			mutex.give();
		}
	}


	public void count() {
		// TODO Auto-generated method stub
		mutex.take();
		timeS++;
		if (timeS >= 60) {
			timeS -= 60;
			timeM++;
		}
		if (timeM >= 60) {
			timeM -= 60;
			timeH++;
		}
		if (timeH >= 24) {
			timeH -= 24;
		}

		output.showTime((timeH * 10000) + (timeM * 100) + (timeS)); 
		checkAlarm();
		mutex.give();

	}
	public void checkAlarm() {
		if (alarm) {
			if (alarmTime == (timeH * 10000) + (timeM * 100) + (timeS)) { // Dags
				// f�r
				// alarm?
				alarmTimeLeft = 5; // 5 BEEPS
			}

			if (alarmTimeLeft > 0) { // R�knar ner antalet beep
				output.doAlarm(); // BEEP BEEP
				alarmTimeLeft--;
			}

			if (!alarm) { // Tar bort �terst�ende beeps om man alarmet st�ngs av
				// i f�rv�g
				alarmTimeLeft = 0;
			}
		}
	}
}
