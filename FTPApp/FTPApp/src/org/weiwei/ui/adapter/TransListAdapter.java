package org.weiwei.ui.adapter;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.weiwei.application.MyApplication;
import org.weiwei.ftp.exception.FTPIllegalReplyException;
import org.weiwei.model.Task;
import org.weiwei.model.User;
import org.weiwei.service.CoreService;
import org.weiwei.ui.activity.R;
import org.weiwei.ui.view.CirProgressLayout;
import org.weiwei.ui.view.CirProgressLayout.CirProgressClickListener;
import org.weiwei.ui.view.SectionedBaseAdapter;
import org.weiwei.utils.StringUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TransListAdapter extends SectionedBaseAdapter {

	public static final int TRAN_LIST_DOWN = 0;

	public static final int TRAN_LIST_UP = 1;

	private LayoutInflater mInflater;

	private ListView listView;

	private List<Task> mDatas = null; // 未完成队列

	private List<Task> DoneList = null; // 完成队列

	private String[] header = null;

	private Context context;
	/**
	 * 要根据当前的user获取完成队列和未完成队列
	 */
	private User user;

	private CoreService coreService = null;

	// 首先获得的数据要进行处理
	// 即分出正在下载的和下载完成的,那么这个状态如何获取呢？
	// 每次下载有几个动作？
	// 下载中，下载完成后从队列中取出,并将任务添加到完成队列中去

	public TransListAdapter(Context context, MyApplication myApp,
			int translistType) {
		mInflater = LayoutInflater.from(context);
		this.user = myApp.getUser();
		this.coreService = myApp.getCoreService();
		switch (translistType) {
		case TRAN_LIST_DOWN:
			header = new String[] { "正在下载", "下载完成" };
			this.mDatas = coreService.getDownList();
			this.DoneList = coreService.getDownDoneList();
			break;
		case TRAN_LIST_UP:
			header = new String[] { "正在上传", "上传完成" };
			this.mDatas = coreService.getUpList();
			this.DoneList = coreService.getUpDoneList();
			break;
		}

	}

	//
	// public TransListAdapter(Context context, List<Task> datas,User user) {
	// this.context = context;
	// mInflater = LayoutInflater.from(context);
	// mDatas = datas;
	// this.user = user;
	// }
	//
	// public TransListAdapter(Context context,CoreService service,User user){
	// this(context,service.getDownList(),user);
	// this.context = context;
	// coreService = service;
	// DoneList = coreService.getDownDoneList(); //从coreService处获得已经完成的任务队列
	// //但是现在的所有任务信息都是在数据库中获取了
	// }

	public void setListView(ListView l) {
		listView = l;
	}

	@Override
	public Object getItem(int section, int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int section, int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	// 获取表头的数目
	@Override
	public int getSectionCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	// 获取对应表头的数目
	@Override
	public int getCountForSection(int section) {
		int result = 0;
		switch (section) {
		case 0:
			result = mDatas.size(); // 未完成队列
			break;
		case 1:
			result = DoneList.size(); // 已完成队列
			break;
		}
		return result;
	}

	// 绘制section里面的item
	// 要获取正确的position即根据传入的section和position建立正确的数据
	@Override
	public View getItemView(int section, int position, View convertView,
			ViewGroup parent) {
		TransListViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listitem_trans_list,
					parent, false);
			holder = new TransListViewHolder();
			holder.filename = (TextView) convertView
					.findViewById(R.id.id_listitem_trans_list_filename);
			holder.filesize = (TextView) convertView
					.findViewById(R.id.id_listitem_trans_list_filesize);
			holder.state = (TextView) convertView
					.findViewById(R.id.id_listitem_trans_list_state);
			holder.image = (ImageView) convertView
					.findViewById(R.id.id_listitem_trans_list_image);
			holder.progress = (CirProgressLayout) convertView
					.findViewById(R.id.id_listitem_trans_list_progressbar);
			convertView.setTag(holder);
		} else {
			holder = (TransListViewHolder) convertView.getTag();
		}

		if (section == 0) {
			// holder.image.setImageDrawable(mDatas.get(position).getImage());
			Task task = mDatas.get(position);
			holder.filename.setText(task.getTaskName());
			String fs = StringUtils.formatByte(task.getFilesize()); //预计下载
			String done = StringUtils.formatByte(task.getDone());//已经下载
			holder.filesize.setText(done+"/"+fs);
			//根据task的状态来重绘state和progress的信息
			switch(task.getTaskState()){
			case Task.TASK_STATE_GOING:
				holder.state.setText(getTaskState(Task.TASK_STATE_GOING));
				break;
			case Task.TASK_STATE_PAUSE:
				holder.state.setText(getTaskState(Task.TASK_STATE_PAUSE));
				break;
			}
			holder.progress.setCenterImage(task.getTaskState());
			holder.progress.setProgress(task.getProgressValue());
			holder.progress.setCProListener(new CPListener(holder, task));
		} else if (section == 1) {
			// holder.image.setImageDrawable(mDatas.get(position).getImage());
			holder.filename.setText(DoneList.get(position).getTaskName());
			holder.filesize.setText(StringUtils.formatByte(DoneList.get(
					position).getFilesize()));
			holder.state.setText(getTaskState(Task.TASK_STATE_FINISH));
			holder.progress.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	// 绘制section 的header
	@Override
	public View getSectionHeaderView(int section, View convertView,
			ViewGroup parent) {
		HeaderViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.listitem_trans_list_header, parent, false);
			holder = new HeaderViewHolder();
			holder.tx = (TextView) convertView
					.findViewById(R.id.id_listitem_trans_list_header_tv);
			convertView.setTag(holder);
		} else {
			holder = (HeaderViewHolder) convertView.getTag();
		}
		holder.tx.setText(header[section]);
		return convertView;
	}

	protected final class HeaderViewHolder {
		TextView tx;
	}

	// 要更新就要把这个handler对象交给其他线程
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Task task = (Task) msg.obj; // 获取当前task的引用
				updateItem(task);// 利用当前task更新对应的数据
				break;
			}

		}

	};

	// 更新某个UI,只有在可见时更新
	private void updateItem(Task task) {
		int visiblePos = listView.getFirstVisiblePosition();// 获取当前可见的第一个item
		int index = mDatas.indexOf(task);// 获取当前task在mDatas中的位置(关键)
		if (index == -1)
			return;
		int offset = getTaskPositon(index) - visiblePos; // 计算偏移量,这个偏移量是否是正确的？
		// 只有在可见区域才更新
		int progressValue = task.getProgressValue();
		if (offset < 0 || offset >= listView.getChildCount()) {
			if (progressValue >= 100) {
				DoneList.add(task);// 将完成的任务移动到完成队列
				mDatas.remove(task);// 从下载队列中移除

				notifyDataSetChanged();
			}
			return;
		}
		View view = listView.getChildAt(offset); // 要注意判断越界错误
		TransListViewHolder holder = (TransListViewHolder) view.getTag();
		holder.filename.setText(task.getTaskName());
		String fs = StringUtils.formatByte(task.getFilesize()); //预计下载
		String done = StringUtils.formatByte(task.getDone());//已经下载
		holder.filesize.setText(done+"/"+fs);
		holder.progress.setProgress(progressValue);
		holder.progress.setCenterImage(task.getTaskState());
		holder.state.setText(getTaskState(task.getTaskState()));
		if (progressValue >= 100) {
			DoneList.add(task);// 将完成的任务移动到完成队列
			mDatas.remove(task);// 从下载队列中移除
			notifyDataSetChanged();
		}

	}

	private int getTaskPositon(int index) {
		return index + 1; // 在listview中item的位置
	}

	/**
	 * 设置当前user中各task的handler来更新进度条
	 */
	public void setHandler() {
		if (user != null) {
			Log.i("TAG", "adpate is set handler");
			// user.setHandler(mHandler);
			Iterator<Task> it = mDatas.iterator();
			while (it.hasNext()) {
				Task task = it.next();
				task.setHandler(mHandler);// 给每个task设置handler
			}
		}
	}

	/**
	 * 移除task的handler
	 */
	public void removeHandler() {
		if (user != null) {
			user.removeHandler();
		}
	}

	/**
	 * 查找在mDatas中没有加入到doneList的 task
	 */
	public void resetList() {
		// 有一个问题是当mDatas进行remover时,不允许进行迭代
		Iterator<Task> it = mDatas.iterator();
		while (it.hasNext()) {
			Task task = it.next();
			if (task.getProgressValue() >= 100) {
				DoneList.add(task);// 将完成的任务移动到完成队列
				// mDatas.remove(task);//从下载队列中移除
				it.remove();// 注意这里为什么不采用mDatas.remove
			}
		}

		notifyDataSetChanged();
	}

	/**
	 * 
	 */
	private String getTaskState(int state) {
		String result = "";
		switch (state) {
		case Task.TASK_STATE_READY:
			result = "准备任务";
			break;
		case Task.TASK_STATE_PAUSE:
			result = "任务暂停";
			break;
		case Task.TASK_STATE_STOP:
			result = "任务完成";
			break;
		case Task.TASK_STATE_FINISH:
			result = "任务完成";
			break;
		case Task.TASK_STATE_GOING:
			result = "正在传输";
			break;
		}
		return result;
	}

	class CPListener implements CirProgressClickListener {
		TransListViewHolder holder = null;
		Task task = null;

		public CPListener(TransListViewHolder holder, Task task) {
			this.holder = holder;
			this.task = task;
		}

		@Override
		public void click() {
			try {
				// TODO Auto-generated method stub
				switch (task.getTaskState()) {
				case Task.TASK_STATE_READY:
					holder.state.setText("暂停任务");
					task.setTaskState(Task.TASK_STATE_PAUSE);
					break;
				case Task.TASK_STATE_STOP:
					holder.state.setText("正在传输");
					task.setTaskState(Task.TASK_STATE_GOING);
					break;
				case Task.TASK_STATE_GOING:
					holder.state.setText("暂停任务");
					task.setTaskState(Task.TASK_STATE_PAUSE);
					// 暂停当前任务
					task.getClient().abortCurrentDataTransfer(true);

					break;
				case Task.TASK_STATE_PAUSE:
					holder.state.setText("正在传输");
					task.setTaskState(Task.TASK_STATE_GOING);
					//重新传输需要给CoreService重新添加任务
					coreService.addTask(task, user);
					
					//如果再次点击
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FTPIllegalReplyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public List<Task> getmDatas() {
		return mDatas;
	}

	public void setmDatas(List<Task> mDatas) {
		this.mDatas = mDatas;
	}

	public List<Task> getDoneList() {
		return DoneList;
	}

	public void setDoneList(List<Task> doneList) {
		DoneList = doneList;
	}

	
}
