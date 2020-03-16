package com.joyhonest.wifi_check;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.p000v4.view.PagerAdapter;
import android.support.p000v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.VideoView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

public class Grid_View extends Activity {
    private static final int OP_Hide_Info_Dialog = 85;
    private static final int OP_Update_Grid = 8;
    private static final int Status_Fail = 3;
    private static final int Status_Progress = 4;
    private static final int Status_Start = 1;
    private static final int Status_Success = 2;
    /* access modifiers changed from: private */
    public VideoView Brow_videoView;
    /* access modifiers changed from: private */
    public RelativeLayout DeleteAert;
    /* access modifiers changed from: private */
    public TextView TitleText_View;
    private WaitView WaitView;
    private Button alart_cancel;
    private Button alart_ok;
    private boolean bExit;
    /* access modifiers changed from: private */
    public Button button_cancel;
    /* access modifiers changed from: private */
    public Button button_delete;
    /* access modifiers changed from: private */
    public GridView gridView;
    /* access modifiers changed from: private */
    public int index;
    private List<String> local_PhotolistFiles;
    private List<String> local_VideolistFiles;
    /* access modifiers changed from: private */
    public List<String> mList;

    /* renamed from: mc */
    MediaController f21mc;
    /* access modifiers changed from: private */
    public MyAdapter myAdapter;
    /* access modifiers changed from: private */
    public List<MyNode> nodes;
    /* access modifiers changed from: private */
    public ViewPager photo_vp;

    class MapComparator implements Comparator<String> {
        MapComparator() {
        }

        public int compare(String str, String str2) {
            return str.compareTo(str2);
        }
    }

    class MyAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater mInflater;
        private List<MyNode> mfilelist;
        private int viewResourceId;

        class MyViewHolder {
            ImageView SelectIcon;
            TextView caption;
            ImageView icon;
            ProgressBar progressBar;
            String sUrl;
            ImageView video_bg;

            MyViewHolder() {
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        MyAdapter(Context context2, int i, List<MyNode> list) {
            this.context = context2;
            this.viewResourceId = i;
            this.mfilelist = list;
            this.mInflater = LayoutInflater.from(context2);
        }

        public int getCount() {
            return this.mfilelist.size();
        }

        public Object getItem(int i) {
            return Integer.valueOf(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            MyViewHolder myViewHolder;
            MyNode myNode = (MyNode) this.mfilelist.get(i);
            if (view == null) {
                view = this.mInflater.inflate(this.viewResourceId, null);
                myViewHolder = new MyViewHolder();
                myViewHolder.progressBar = (ProgressBar) view.findViewById(C0287R.C0289id.Grid_progressBar1);
                myViewHolder.progressBar.setProgress(0);
                myViewHolder.progressBar.setMax(1000);
                myViewHolder.icon = (ImageView) view.findViewById(C0287R.C0289id.Grid_imageView1);
                myViewHolder.video_bg = (ImageView) view.findViewById(C0287R.C0289id.video_bg);
                myViewHolder.caption = (TextView) view.findViewById(C0287R.C0289id.Grid_textView1);
                myViewHolder.SelectIcon = (ImageView) view.findViewById(C0287R.C0289id.image_Select);
                myViewHolder.sUrl = "";
                view.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) view.getTag();
            }
            if (myNode != null) {
                if (myNode.nSelectType == 1) {
                    myViewHolder.SelectIcon.setVisibility(0);
                    myViewHolder.SelectIcon.setBackgroundResource(C0287R.mipmap.selectedflag_no);
                } else if (myNode.nSelectType == 2) {
                    myViewHolder.SelectIcon.setVisibility(0);
                    myViewHolder.SelectIcon.setBackgroundResource(C0287R.mipmap.selectedflag_yes);
                } else {
                    myViewHolder.SelectIcon.setVisibility(4);
                }
                if (myNode.bitmap != null) {
                    myViewHolder.icon.setImageBitmap(myNode.bitmap);
                } else {
                    myViewHolder.icon.setImageBitmap(BitmapFactory.decodeResource(this.context.getResources(), C0287R.mipmap.background));
                }
                if (myNode.nType == MyNode.TYPE_Remote) {
                    myViewHolder.progressBar.setVisibility(0);
                    if (myNode.nStatus == 2) {
                        myViewHolder.progressBar.setProgress(1000);
                        myViewHolder.caption.setVisibility(4);
                    } else if (myNode.nStatus == 1) {
                        myViewHolder.progressBar.setProgress((int) myNode.nPre);
                        myViewHolder.caption.setTextColor(-16776961);
                        myViewHolder.caption.setVisibility(0);
                        if (myNode.nPre < 10) {
                            myNode.nPre = 10;
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append(Grid_View.this.getString(C0287R.string.downloading));
                        sb.append(" ");
                        sb.append(myNode.nPre / 10);
                        sb.append("%");
                        myViewHolder.caption.setText(sb.toString());
                    } else {
                        myViewHolder.progressBar.setProgress(0);
                        myViewHolder.caption.setVisibility(4);
                    }
                } else {
                    myViewHolder.progressBar.setVisibility(4);
                    myViewHolder.caption.setVisibility(4);
                }
            }
            if (MyApp.nBrowType == MyApp.Brow_Video) {
                myViewHolder.video_bg.setVisibility(0);
            }
            return view;
        }
    }

    private class _Init_Theard extends Thread {
        private _Init_Theard() {
        }

        public void run() {
            Grid_View.this.F_Init();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C0287R.layout.activity_grid_view);
        MyApp.checkDeviceHasNavigationBar(this);
        this.DeleteAert = (RelativeLayout) findViewById(C0287R.C0289id.DeleteAert);
        this.alart_cancel = (Button) findViewById(C0287R.C0289id.alart_cancel);
        this.alart_ok = (Button) findViewById(C0287R.C0289id.alart_ok);
        this.DeleteAert.setVisibility(4);
        this.button_cancel = (Button) findViewById(C0287R.C0289id.button_cancel);
        this.button_delete = (Button) findViewById(C0287R.C0289id.button_delete);
        this.button_cancel.setVisibility(4);
        this.button_delete.setVisibility(4);
        this.WaitView = (WaitView) findViewById(C0287R.C0289id.WaitView);
        this.WaitView.setVisibility(4);
        this.TitleText_View = (TextView) findViewById(C0287R.C0289id.Title_textView);
        this.gridView = (GridView) findViewById(C0287R.C0289id.gridView1);
        this.photo_vp = (ViewPager) findViewById(C0287R.C0289id.photo_vp);
        this.Brow_videoView = (VideoView) findViewById(C0287R.C0289id.Brow_videoView2);
        this.Brow_videoView.setVisibility(4);
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.addRule(12);
        layoutParams.addRule(10);
        layoutParams.addRule(9);
        layoutParams.addRule(11);
        this.Brow_videoView.setLayoutParams(layoutParams);
        this.gridView.setVisibility(0);
        this.f21mc = new MediaController(this);
        this.Brow_videoView.setMediaController(this.f21mc);
        this.local_PhotolistFiles = new ArrayList();
        this.local_VideolistFiles = new ArrayList();
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        this.gridView.setNumColumns((point.x / Storage.dip2px(this, 100.0f)) - 1);
        this.gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                Grid_View.this.index = i;
                if (!Grid_View.this.isinSelectMode()) {
                    for (MyNode myNode : Grid_View.this.nodes) {
                        myNode.nSelectType = 1;
                    }
                    ((MyNode) Grid_View.this.nodes.get(Grid_View.this.index)).nSelectType = 2;
                    Grid_View.this.myAdapter.notifyDataSetChanged();
                    Grid_View.this.button_cancel.setVisibility(0);
                    Grid_View.this.button_delete.setVisibility(0);
                }
                return true;
            }
        });
        this.gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                MyApp.F_PlayBtnVoice();
                MyNode myNode = (MyNode) Grid_View.this.nodes.get(i);
                if (Grid_View.this.isinSelectMode()) {
                    if (myNode.nSelectType == 1) {
                        myNode.nSelectType = 2;
                    } else {
                        myNode.nSelectType = 1;
                    }
                    Grid_View.this.myAdapter.notifyDataSetChanged();
                } else if (MyApp.nBrowType == MyApp.Brow_Photo) {
                    Grid_View.this.TitleText_View.setVisibility(4);
                    Grid_View.this.Brow_videoView.setVisibility(4);
                    Grid_View.this.gridView.setVisibility(4);
                    Grid_View.this.photo_vp.setAdapter(new PagerAdapter() {
                        public boolean isViewFromObject(View view, Object obj) {
                            return view == obj;
                        }

                        public int getCount() {
                            return Grid_View.this.nodes.size();
                        }

                        public void destroyItem(ViewGroup viewGroup, int i, @NonNull Object obj) {
                            viewGroup.removeView((View) obj);
                        }

                        public Object instantiateItem(ViewGroup viewGroup, int i) {
                            MyNode myNode = (MyNode) Grid_View.this.nodes.get(i);
                            ImageView imageView = new ImageView(Grid_View.this);
                            imageView.setBackground(new BitmapDrawable(Grid_View.this.getResources(), Grid_View.this.LoadBitmap(myNode.sPath)));
                            imageView.setOnClickListener(new OnClickListener() {
                                public void onClick(View view) {
                                    Grid_View.this.photo_vp.setVisibility(8);
                                }
                            });
                            viewGroup.addView(imageView);
                            return imageView;
                        }
                    });
                    Grid_View.this.photo_vp.setCurrentItem(i);
                    Grid_View.this.photo_vp.setVisibility(0);
                } else {
                    Grid_View.this.TitleText_View.setVisibility(4);
                    Grid_View.this.Brow_videoView.setVisibility(0);
                    Grid_View.this.gridView.setVisibility(4);
                    Grid_View.this.Brow_videoView.setVideoPath(myNode.sPath);
                    Grid_View.this.Brow_videoView.start();
                    Grid_View.this.Brow_videoView.requestFocus();
                }
            }
        });
        if (MyApp.nBrowType == MyApp.Brow_Photo) {
            this.TitleText_View.setText(C0287R.string.brow_photos);
        } else {
            this.TitleText_View.setText(C0287R.string.brow_videos);
        }
        findViewById(C0287R.C0289id.but_exit).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MyApp.F_PlayBtnVoice();
                Grid_View.this.F_SetBtnImg(C0287R.C0289id.but_exit, C0287R.mipmap.back_b, C0287R.mipmap.back_b, 200);
                Grid_View.this.Exit();
            }
        });
        this.alart_cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MyApp.F_PlayBtnVoice();
                Grid_View.this.DeleteAert.setVisibility(4);
            }
        });
        this.alart_ok.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MyApp.F_PlayBtnVoice();
                Grid_View.this.deleteSelectFile();
                Grid_View.this.DeleteAert.setVisibility(4);
                Grid_View.this.resetdeleteSelectFile();
            }
        });
        this.button_delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                int i = 0;
                for (MyNode myNode : Grid_View.this.nodes) {
                    if (myNode.nSelectType == 2) {
                        i++;
                    }
                }
                if (i > 0) {
                    MyApp.F_PlayBtnVoice();
                    Grid_View.this.DeleteAert.setVisibility(0);
                }
            }
        });
        this.button_cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MyApp.F_PlayBtnVoice();
                Grid_View.this.resetdeleteSelectFile();
            }
        });
        this.bExit = false;
        new _Init_Theard().start();
        EventBus.getDefault().register(this);
    }

    /* access modifiers changed from: private */
    public void deleteSelectFile() {
        if (this.nodes.size() != 0) {
            for (int size = this.nodes.size() - 1; size >= 0; size--) {
                MyNode myNode = (MyNode) this.nodes.get(size);
                String str = myNode.sPath;
                if (myNode.nSelectType == 2) {
                    this.nodes.remove(size);
                    if (size < this.mList.size()) {
                        this.mList.remove(size);
                    }
                    MyApp.DeleteImage(str);
                    String fileName = getFileName(str);
                    StringBuilder sb = new StringBuilder();
                    sb.append(getCacheDir());
                    sb.append(fileName);
                    sb.append(".thb.png");
                    String sb2 = sb.toString();
                    if (MyApp.nBrowType == MyApp.Brow_Video) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(getCacheDir());
                        sb3.append("/");
                        sb3.append(fileName);
                        sb3.append(".v_thb.png");
                        sb2 = sb3.toString();
                    }
                    File file = new File(sb2);
                    if (file.exists()) {
                        file.delete();
                    }
                    if (this.nodes.size() == 0) {
                        this.TitleText_View.setText(C0287R.string.NoFile);
                    } else {
                        this.TitleText_View.setText(String.format(getString(C0287R.string.Total_files), new Object[]{Integer.valueOf(this.nodes.size())}));
                    }
                    this.myAdapter.notifyDataSetChanged();
                }
            }
            resetdeleteSelectFile();
        }
    }

    /* access modifiers changed from: private */
    public void resetdeleteSelectFile() {
        for (MyNode myNode : this.nodes) {
            myNode.nSelectType = 0;
        }
        this.myAdapter.notifyDataSetChanged();
        this.button_cancel.setVisibility(4);
        this.button_delete.setVisibility(4);
    }

    /* access modifiers changed from: private */
    public boolean isinSelectMode() {
        for (MyNode myNode : this.nodes) {
            if (myNode.nSelectType != 0) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /* access modifiers changed from: private */
    public Bitmap LoadBitmap(String str) {
        Options options = new Options();
        int i = 1;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inJustDecodeBounds = false;
        int i2 = (int) (((float) options.outHeight) / 1280.0f);
        if (i2 > 0) {
            i = i2;
        }
        options.inSampleSize = i;
        return BitmapFactory.decodeFile(str, options);
    }

    /* access modifiers changed from: private */
    public void F_SetBtnImg(int i, int i2, final int i3, int i4) {
        final View findViewById = findViewById(i);
        if (findViewById != null) {
            findViewById.setBackgroundResource(i2);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    findViewById.setBackgroundResource(i3);
                }
            }, (long) i4);
        }
    }

    /* access modifiers changed from: private */
    public void Exit() {
        if (this.gridView.getVisibility() == 4) {
            this.Brow_videoView.stopPlayback();
            this.Brow_videoView.setVisibility(4);
            this.photo_vp.setVisibility(4);
            this.gridView.setVisibility(0);
            this.TitleText_View.setVisibility(0);
            MyApp.checkDeviceHasNavigationBar(this);
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, BrowSelect.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() != 4 || keyEvent.getAction() != 0 || keyEvent.getRepeatCount() != 0) {
            return super.dispatchKeyEvent(keyEvent);
        }
        Exit();
        return true;
    }

    /* access modifiers changed from: private */
    public void F_Init() {
        this.WaitView.setVisibility(0);
        int i = MyApp.nBrowType;
        if (i == MyApp.Brow_Photo) {
            F_GetAllPhotoLocal();
            List<MyNode> list = this.nodes;
            if (list != null) {
                list.clear();
            }
            this.nodes = new ArrayList();
            List<String> list2 = this.mList;
            if (list2 != null) {
                for (String str : list2) {
                    if (this.bExit) {
                        break;
                    }
                    Bitmap GetSuonuitu = GetSuonuitu(str);
                    MyNode myNode = new MyNode(i);
                    myNode.bitmap = GetSuonuitu;
                    myNode.sPath = str;
                    this.nodes.add(myNode);
                }
            }
        } else {
            F_GetAllVideoLocal();
            List<MyNode> list3 = this.nodes;
            if (list3 != null) {
                list3.clear();
            }
            this.nodes = new ArrayList();
            List<String> list4 = this.mList;
            if (list4 != null) {
                for (String str2 : list4) {
                    if (this.bExit) {
                        break;
                    }
                    Bitmap videoThumbnail = getVideoThumbnail(str2);
                    MyNode myNode2 = new MyNode(0);
                    myNode2.bitmap = videoThumbnail;
                    myNode2.sPath = str2;
                    this.nodes.add(myNode2);
                }
            }
        }
        runOnUiThread(new Runnable() {
            public void run() {
                if (Grid_View.this.nodes.size() == 0) {
                    Grid_View.this.TitleText_View.setText(C0287R.string.NoFile);
                    return;
                }
                Grid_View.this.TitleText_View.setText(String.format(Grid_View.this.getString(C0287R.string.Total_files), new Object[]{Integer.valueOf(Grid_View.this.nodes.size())}));
            }
        });
        if (this.bExit) {
            List<MyNode> list5 = this.nodes;
            if (list5 != null) {
                list5.clear();
            }
            return;
        }
        EventBus.getDefault().post("abd", "OP_Hide_Info_Dialog");
    }

    private Bitmap getVideoThumbnail(String str) {
        String fileName = getFileName(str);
        StringBuilder sb = new StringBuilder();
        sb.append(getCacheDir());
        sb.append("/");
        sb.append(fileName);
        sb.append(".v_thb.png");
        String sb2 = sb.toString();
        Bitmap decodeFile = BitmapFactory.decodeFile(sb2);
        if (decodeFile != null) {
            return decodeFile;
        }
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(str);
            Bitmap extractThumbnail = ThumbnailUtils.extractThumbnail(mediaMetadataRetriever.getFrameAtTime(1), 100, 100);
            try {
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            decodeFile = extractThumbnail;
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        } catch (RuntimeException e3) {
            e3.printStackTrace();
            try {
            } catch (RuntimeException e4) {
                e4.printStackTrace();
            }
        } finally {
            try {
                mediaMetadataRetriever.release();
            } catch (RuntimeException e5) {
                e5.printStackTrace();
            }
        }
        F_SaveBitmap(decodeFile, sb2);
        return decodeFile;
    }

    private Bitmap GetSuonuitu(String str) {
        String fileName = getFileName(str);
        StringBuilder sb = new StringBuilder();
        sb.append(getCacheDir());
        sb.append("/");
        sb.append(fileName);
        sb.append(".thb.png");
        String sb2 = sb.toString();
        Bitmap decodeFile = BitmapFactory.decodeFile(sb2);
        if (decodeFile != null) {
            return decodeFile;
        }
        Options options = new Options();
        int i = 1;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inJustDecodeBounds = false;
        int i2 = (int) (((float) options.outHeight) / 100.0f);
        if (i2 > 0) {
            i = i2;
        }
        options.inSampleSize = i;
        Bitmap decodeFile2 = BitmapFactory.decodeFile(str, options);
        F_SaveBitmap(decodeFile2, sb2);
        return decodeFile2;
    }

    /* access modifiers changed from: private */
    public String getFileName(String str) {
        int lastIndexOf = str.lastIndexOf("/");
        int lastIndexOf2 = str.lastIndexOf(".");
        if (lastIndexOf == -1 || lastIndexOf2 == -1) {
            return null;
        }
        return str.substring(lastIndexOf + 1);
    }

    private void F_SaveBitmap(Bitmap bitmap, String str) {
        String str2 = "WIFI";
        Log.e(str2, "保存图片");
        if (bitmap != null) {
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(CompressFormat.PNG, 90, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                Log.i(str2, "已经保存");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void F_GetAllPhotoLocal() {
        String str = MyApp.sLocalPhoto;
        this.local_PhotolistFiles.clear();
        if (str != null) {
            File[] listFiles = new File(str).listFiles();
            for (File path : listFiles) {
                this.local_PhotolistFiles.add(path.getPath());
            }
        }
        this.mList = this.local_PhotolistFiles;
        Collections.sort(this.mList, new MapComparator());
    }

    private void F_GetAllVideoLocal() {
        String str = MyApp.sLocalVideo;
        this.local_VideolistFiles.clear();
        if (str != null) {
            for (File path : new File(str).listFiles()) {
                this.local_VideolistFiles.add(path.getPath());
            }
        }
        this.mList = this.local_VideolistFiles;
        Collections.sort(this.mList, new MapComparator());
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
    }

    @Subscriber(tag = "OP_Hide_Info_Dialog")
    private void OP_Hide_Info_Dialog(String str) {
        this.myAdapter = new MyAdapter(this, C0287R.layout.my_grid_node, this.nodes);
        this.gridView.setAdapter(this.myAdapter);
        this.myAdapter.notifyDataSetChanged();
        this.WaitView.setVisibility(4);
    }

    /* access modifiers changed from: protected */
    public void Alert_dialog() {
        Builder builder = new Builder(this);
        builder.setMessage(C0287R.string.Sure_you_want_to_delete);
        builder.setPositiveButton(C0287R.string.f23OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (Grid_View.this.index >= 0 && Grid_View.this.index < Grid_View.this.nodes.size()) {
                    MyNode myNode = (MyNode) Grid_View.this.nodes.get(Grid_View.this.index);
                    Grid_View.this.nodes.remove(Grid_View.this.index);
                    if (Grid_View.this.mList != null && Grid_View.this.index < Grid_View.this.mList.size()) {
                        Grid_View.this.mList.remove(Grid_View.this.index);
                    }
                    String str = myNode.sPath;
                    MyApp.DeleteImage(str);
                    String access$1900 = Grid_View.this.getFileName(str);
                    StringBuilder sb = new StringBuilder();
                    sb.append(Grid_View.this.getCacheDir());
                    sb.append(access$1900);
                    sb.append(".thb.png");
                    String sb2 = sb.toString();
                    if (MyApp.nBrowType == MyApp.Brow_Video) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(Grid_View.this.getCacheDir());
                        sb3.append("/");
                        sb3.append(access$1900);
                        sb3.append(".v_thb.png");
                        sb2 = sb3.toString();
                    }
                    File file = new File(sb2);
                    if (file.exists()) {
                        file.delete();
                    }
                    if (Grid_View.this.nodes.size() == 0) {
                        Grid_View.this.TitleText_View.setText(C0287R.string.NoFile);
                    } else {
                        Grid_View.this.TitleText_View.setText(String.format(Grid_View.this.getString(C0287R.string.Total_files), new Object[]{Integer.valueOf(Grid_View.this.nodes.size())}));
                    }
                    Grid_View.this.myAdapter.notifyDataSetChanged();
                }
                MyApp.checkDeviceHasNavigationBar(Grid_View.this);
            }
        });
        builder.setNegativeButton(C0287R.string.Cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                MyApp.checkDeviceHasNavigationBar(Grid_View.this);
            }
        });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        create.show();
    }

    private static List<Bitmap> getDiskBitmap() {
        ArrayList arrayList = new ArrayList();
        String[] imageNames = getImageNames("sdcard/SKY_EYE/Photo");
        String[] strArr = new String[imageNames.length];
        for (int i = 0; i < imageNames.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("sdcard/SKY_EYE/Photo/");
            sb.append(imageNames[i]);
            strArr[i] = sb.toString();
        }
        for (String decodeFile : strArr) {
            arrayList.add(BitmapFactory.decodeFile(decodeFile));
        }
        return arrayList;
    }

    public static String[] getImageNames(String str) {
        String str2;
        String[] list = new File(str).list();
        int i = 0;
        int i2 = 0;
        while (true) {
            str2 = "/";
            if (i >= list.length) {
                break;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(str2);
            sb.append(list[i]);
            File file = new File(sb.toString());
            if (!file.isDirectory() && isImageFile(file.getName())) {
                i2++;
            }
            i++;
        }
        String[] strArr = new String[i2];
        int i3 = 0;
        for (String append : list) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(str2);
            sb2.append(append);
            File file2 = new File(sb2.toString());
            if (!file2.isDirectory() && isImageFile(file2.getName())) {
                strArr[i3] = file2.getName();
                i3++;
            }
        }
        return strArr;
    }

    private static boolean isImageFile(String str) {
        String substring = str.substring(str.lastIndexOf(".") + 1, str.length());
        if (!substring.equalsIgnoreCase("jpg") && !substring.equalsIgnoreCase("png") && !substring.equalsIgnoreCase("bmp") && !substring.equalsIgnoreCase("mp4")) {
            return false;
        }
        return true;
    }
}
