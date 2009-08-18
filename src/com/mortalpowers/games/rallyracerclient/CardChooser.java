package com.mortalpowers.games.rallyracerclient;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CardChooser extends View {
	public ArrayList<Message> cards;
	Message inMotion = null;

	public CardChooser(Context context) {
		super(context);
		setFocusable(true); // necessary for getting the touch events

		// TODO Auto-generated constructor stub
		cards = new ArrayList<Message>();
	}

	protected void onDraw(Canvas canvas) {
		// canvas.drawColor(0xFFCCCCCC); //if you want another background color
		for (Message c : cards) {
			canvas.drawText(c.msg, c.x, c.y, c.p);
		}
		Paint white = new Paint();
		white.setARGB(255, 200, 0, 0);
		canvas.drawRect(30, 375, 300, 400, white);
	}

	public void cleanUp() {
		int i = 50;
		for (Message m : cards) {
			m.y = i;
			i += 75;
		}
		invalidate();
	}

	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();

		int X = (int) event.getX();
		int Y = (int) event.getY();

		switch (eventaction) {

		case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on
			if (Y < 375) {
				for (Message m : cards) {
					// check if inside the bounds of the ball (circle)
					// get the center for the ball
					if (m == null) {
						Log.e("cardevent", "Why was the card m null?!");
					}
					float centerY = m.y - 5;

					if (Math.abs(centerY - Y) < 20) {
						invalidate();
						inMotion = m;
						break;
					}

				}
			} else {
				
			}

			break;

		case MotionEvent.ACTION_MOVE: // touch message with the finger
			if (inMotion != null) {
				inMotion.y = Y + 15;
				invalidate();
			}

			break;

		case MotionEvent.ACTION_UP:
			Log.e("cardevent", "Up at " + Y);
			if (inMotion != null) {
				cards.remove(inMotion);
				int i = 0;
				for (Message m : cards) {

					if (inMotion.y < m.y) {
						break;
					}
					i++;

				}

				cards.add(i, inMotion);
				inMotion = null;
				cleanUp();
			}

			break;
		}
		return true;

	}

	static public class Message {
		String msg;
		float x, y;
		Paint p;

		public Message(String s) {
			msg = s;
			p = new Paint();
			p.setStrokeWidth(2);
			p.setARGB(255, 200, 200, 200);
			p.setTextSize(42);
		}
	}
}
