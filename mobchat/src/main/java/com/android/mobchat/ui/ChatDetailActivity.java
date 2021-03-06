package com.android.mobchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.mobchat.R;
import com.android.mobchat.activitys.BaseChatActivity;
import com.android.mobchat.utils.Utils;
import com.mob.imsdk.model.IMGroup;
import com.mob.imsdk.model.IMUser;

public class ChatDetailActivity extends BaseChatActivity {

	//private String id ;
	private boolean isGroup;

	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frame);
		Intent intent = getIntent();
		if (intent != null) {
			//		id = getIntent().getExtras().getString("id");
			isGroup = getIntent().getExtras().getBoolean("isgroup");
			IMGroup group = null;
			IMUser user = null;
			ChatDetailFragment chatFragment = null;
			if (isGroup) {
				group = (IMGroup) getIntent().getExtras().getSerializable("group");
				Utils.showLog("chatdetailAct", " ===== group.getId() " + group.getId());
				chatFragment = ChatDetailFragment.instace(group);
			} else {
				user = (IMUser) getIntent().getExtras().getSerializable("user");
				Utils.showLog("chatdetailAct", " ===== user.getId() " + user.getId());
				chatFragment = ChatDetailFragment.instace(user);
			}


			if (chatFragment != null) {
				getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, chatFragment).commit();
			}
		}
	}
}
