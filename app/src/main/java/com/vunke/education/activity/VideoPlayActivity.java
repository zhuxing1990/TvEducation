package com.vunke.education.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.vunke.education.R;
import com.vunke.education.base.BaseActivity;

public class VideoPlayActivity extends BaseActivity {

	private final String TAG = "main";
	private SurfaceView sv;
	private TextView btn_play;
	private int duration;
	private MediaPlayer mediaPlayer;
	private ProgressBar seekBar;
	private int currentPosition = 0;
	private boolean isPlaying;
	private Thread thread;
	private RelativeLayout ly;
	private int seekbarInt = 0;
	private int seekbarIntL = 0;
	private boolean startPlay = false;
	TextView startTime;
	TextView endTime;
	ImageView playImageView;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Log.e("move", msg.arg1 + "----接收到消息");
			if (msg.arg1 == 0x0003) {
				// if (msg.arg1 == 0x0000) {
				// 刷新精度条
				// int current = mediaPlayer.getCurrentPosition();

				seekBar.setProgress(msg.what);
				// }else {
				// //停止刷新进度条
				// }
			}

		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_videoplay);
		seekBar = (ProgressBar) findViewById(R.id.seekBar);
		mediaPlayer = new MediaPlayer();
		sv = (SurfaceView) findViewById(R.id.sv);
		btn_play = (TextView) findViewById(R.id.btn_play);
		// btn_pause = (Button) findViewById(R.id.btn_pause);
		// btn_replay = (Button) findViewById(R.id.btn_replay);
		// btn_stop = (Button) findViewById(R.id.btn_stop);
		startTime = (TextView) findViewById(R.id.btn_startTime);
		startTime.setFocusable(false);
		endTime = (TextView) findViewById(R.id.btn_EndTime);
		endTime.setFocusable(false);
		playImageView = (ImageView) findViewById(R.id.imageViewPlay);
		playImageView.setFocusable(true);
		playImageView.requestFocus();
		ly = (RelativeLayout) findViewById(R.id.ly);
		playImageView.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				Message msg = new Message();
				if (arg1 == 22 && arg2.getAction() == KeyEvent.ACTION_DOWN) {
					int progress = seekBar.getProgress();
					msg.arg1 = 0x0003;
					seekbarInt = progress + 10000;
					msg.what = seekbarInt;
					if (seekbarInt < duration) {
						handler.sendMessage(msg);
					}
					if (mediaPlayer != null && mediaPlayer.isPlaying()) {
						mediaPlayer.pause();
						playImageView.setBackgroundResource(R.drawable.stop);
						btn_play.setText("继续");
					}

					// isPlaying = false;
				} else if ((arg1 == 21 || arg1 == 22)
						&& arg2.getAction() == KeyEvent.ACTION_UP) {
					if (startPlay) {
						if (arg1 == 21) {
							mediaPlayer.seekTo(seekbarIntL + 100);
						} else {
							mediaPlayer.seekTo(seekbarInt - 100);
						}

						mediaPlayer.start();
						playImageView.setBackgroundResource(R.drawable.play);
						btn_play.setText("暂停");
						new Thread() {
							@Override
							public void run() {
								try {
									while (mediaPlayer != null && mediaPlayer.isPlaying()) {
										Message msg = new Message();
										msg.arg1 = 0x0003;
										msg.arg2 = 0x0000;
										msg.what = mediaPlayer
												.getCurrentPosition();
										handler.sendMessage(msg);
										sleep(500);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}.start();
					}else {
						if (arg1 == 21) {
							mediaPlayer.seekTo(seekbarIntL + 100);
						} else {
							mediaPlayer.seekTo(seekbarInt - 100);
						}
					}
				} else if (arg1 == 21
						&& arg2.getAction() == KeyEvent.ACTION_DOWN) {
					Message msg1 = new Message();
					int progress = seekBar.getProgress();
					Log.e("move", "1111---->" + progress);
					msg1.arg1 = 0x0003;
					seekbarIntL = progress - 500;
					msg1.what = seekbarIntL;
//					if (seekbarIntL < 0) {
					handler.sendMessage(msg1);
//					}
					if (mediaPlayer != null && mediaPlayer.isPlaying()) {
						mediaPlayer.pause();
						playImageView.setBackgroundResource(R.drawable.video_stop);
						btn_play.setText("继续");
					}
				}
				return false;
			}
		});
		playImageView.setOnClickListener(click);
		// btn_pause.setOnClickListener(click);
		// btn_replay.setOnClickListener(click);
		// btn_stop.setOnClickListener(click);

		// 为SurfaceHolder添加回调
		sv.getHolder().addCallback(callback);

		// 4.0版本之下需要设置的属性
		// 设置Surface不维护自己的缓冲区，而是等待屏幕的渲染引擎将内容推送到界面
		// sv.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		// 为进度条添加进度更改事件
		// seekBar.setOnSeekBarChangeListener(change);
	}

	private Callback callback = new Callback() {
		// SurfaceHolder被修改的时候回调
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			Log.i(TAG, "SurfaceHolder 被销毁");
			// 销毁SurfaceHolder的时候记录当前的播放位置并停止播放
			if (mediaPlayer != null && mediaPlayer.isPlaying()) {
				currentPosition = mediaPlayer.getCurrentPosition();
				mediaPlayer.stop();
			}
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			Log.i(TAG, "SurfaceHolder 被创建");
			if (currentPosition > 0) {
				// 创建SurfaceHolder的时候，如果存在上次播放的位置，则按照上次播放位置进行播放
				play(currentPosition);
				currentPosition = 0;
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
								   int height) {
			Log.i(TAG, "SurfaceHolder 大小被改变");
		}

	};

	private OnSeekBarChangeListener change = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// 当进度条停止修改的时候触发
			// 取得当前进度条的刻度
			int progress = seekBar.getProgress();
			if (mediaPlayer != null && mediaPlayer.isPlaying()) {
				// 设置当前播放的位置
				mediaPlayer.seekTo(progress);
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

			Log.e("move", "触碰");
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
									  boolean fromUser) {
			Message msg = new Message();
			msg.arg1 = 0x0003;
			msg.arg2 = 0x0001;
			msg.what = seekBar.getProgress();
			handler.sendMessage(msg);
			Log.e("move", "开始移动");

		}
	};

	private View.OnClickListener click = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
				case R.id.imageViewPlay:
					play(0);
					break;
				// case R.id.btn_pause:
				// pauseVideo();
				// break;
				// case R.id.btn_replay:
				// replay();
				// break;
				// case R.id.btn_stop:
				// stopVideo();
				// break;
				default:
					break;
			}
		}
	};

	/*
	 * 停止播放
	 */
	protected void stop() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			mediaPlayer.release();
			mediaPlayer = null;
			btn_play.setEnabled(true);
			isPlaying = false;
		}else if (mediaPlayer!=null && !(mediaPlayer.isPlaying())) {
			isPlaying = false;
			mediaPlayer.release();
			isPlaying = false;
		}
	}
//	String videoPath = "http://live.hcs.cmvideo.cn:8088/wd-hunanhd-1200/01.m3u8?msisdn=3000000000000&mdspid=&spid=699017&netType=5&sid=2201064496&pid=2028595851&timestamp=20170327111900&Channel_ID=0116_22300109-91000-20300&ProgramID=603996975&ParentNodeID=-99&preview=1&playseek=000000-000600&client_ip=123.206.208.186&assertID=2201064496&SecurityKey=20170327111900&mtv_session=cebd4400b57b1ed403b5f6c4704107b4&HlsSubType=1&HlsProfileId=1&encrypt=7e242fdb1db7a9a66d83221440f09cee";

	/**
	 * 开始播放
	 *
	 * @param msec
	 *            播放初始位置
	 */
	protected void play(final int msec) {
		// 获取视频文件地址
//		String path = "http://v.cctv.com/flash/mp4video6/TMS/2011/01/05/cf752b1c12ce452b3040cab2f90bc265_h264818000nero_aac32-1.mp4";
		 String path =
		 "http://10.255.30.137:8082/EDS/RedirectPlay/lutong/vod/lutongCP0664900538/CP0664900538";
		if (btn_play.getText().toString().trim().equals("继续")) {
			playImageView.setBackgroundResource(R.drawable.video_play);
			btn_play.setText("暂停");
			mediaPlayer.start();
			showToast("继续播放");
			new Thread() {
				@Override
				public void run() {
					try {
						while (mediaPlayer != null
								&& mediaPlayer.isPlaying()) {
							Message msg = new Message();
							msg.arg1 = 0x0003;
							msg.arg2 = 0x0000;
							msg.what = mediaPlayer
									.getCurrentPosition();
							handler.sendMessage(msg);
							sleep(500);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
			return;
		} else if (btn_play.getText().toString().trim().equals("暂停")
				&& mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			playImageView.setBackgroundResource(R.drawable.video_stop);
			btn_play.setText("继续");
			showToast("暂停播放");
		} else if (btn_play.getText().toString().trim().equals("播放")
				|| btn_play.getText().toString().trim().equals("重播")) {
			try {

				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				// 设置播放的视频源
				// mediaPlayer.setDataSource(file.getAbsolutePath());
				mediaPlayer.setDataSource(mcontext, Uri.parse(path));
				// 设置显示视频的SurfaceHolder
				mediaPlayer.setDisplay(sv.getHolder());
				Log.i(TAG, "开始装载");
				mediaPlayer.prepareAsync();
				mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
					@Override
					public void onPrepared(MediaPlayer mp) {
						Log.i(TAG, "装载完成");
						mediaPlayer.start();
						// 按照初始位置播放
						mediaPlayer.seekTo(msec);
						duration = mediaPlayer.getDuration();
						startPlay = true;
						if (duration == 0) {
							ly.setVisibility(View.GONE);

						}
						seekBar.setMax(duration);
						thread = new Thread() {
							@Override
							public void run() {
								try {
									while (mediaPlayer != null
											&& mediaPlayer.isPlaying()) {
										Message msg = new Message();
										msg.arg1 = 0x0003;
										msg.arg2 = 0x0000;
										msg.what = mediaPlayer
												.getCurrentPosition();
										handler.sendMessage(msg);
										sleep(500);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						};
						thread.start();
						btn_play.setText("暂停");
						playImageView.setBackgroundResource(R.drawable.video_play);
					}
				});
				mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						// 在播放完毕被回调
						// btn_play.setEnabled(true);
						playImageView.setBackgroundResource(R.drawable.restart);
						showToast("播放完毕");
						// stopVideo();
						mediaPlayer.reset();
						btn_play.setText("重播");
					}
				});

				mediaPlayer.setOnErrorListener(new OnErrorListener() {
					@Override
					public boolean onError(MediaPlayer mp, int what, int extra) {
						// 发生错误重新播放
						play(0);
						isPlaying = false;
						return false;
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 重新开始播放
	 */
	protected void replay() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.seekTo(0);
			showToast("重新播放");
			// btn_pause.setText("暂停");
			return;
		}
		isPlaying = false;
		play(0);

	}

	@Override
	protected void onDestroy() {
		stop();
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (mediaPlayer!=null && mediaPlayer.isPlaying()) {

		}
		mediaPlayer.release();
		mediaPlayer = null;
	}


}
