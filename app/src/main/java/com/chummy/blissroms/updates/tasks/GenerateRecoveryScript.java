/*
 * Copyright (C) 2015 Matt Booth (Kryten2k35).
 *
 * Licensed under the Attribution-NonCommercial-ShareAlike 4.0 International 
 * (the "License") you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://creativecommons.org/licenses/by-nc-sa/4.0/legalcode
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chummy.blissroms.updates.tasks;

import java.io.OutputStream;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ota.updates.R;
import com.chummy.blissroms.updates.RomUpdate;
import com.chummy.blissroms.updates.utils.Constants;
import com.chummy.blissroms.updates.utils.Preferences;
import com.chummy.blissroms.updates.utils.Tools;

public class GenerateRecoveryScript extends AsyncTask<Void, String, Boolean> implements Constants {

	public final String TAG = this.getClass().getSimpleName();

	private Context mContext;
	private ProgressDialog mLoadingDialog;
	private StringBuilder mScript = new StringBuilder();
	private static String SCRIPT_FILE = "/cache/recovery/openrecoveryscript";
	private static String NEW_LINE = "\n";   
	private String mFilename;
	private String mScriptOutput;

	public GenerateRecoveryScript(Context context) {
		mContext = context;
		mFilename = RomUpdate.getFilename(mContext) + ".zip";
	}

	protected void onPreExecute() {
		// Show dialog
		mLoadingDialog = new ProgressDialog(mContext);
		mLoadingDialog.setCancelable(false);
		mLoadingDialog.setIndeterminate(true);
		mLoadingDialog.setMessage(mContext.getString(R.string.rebooting));
		mLoadingDialog.show();

		if (Preferences.getWipeData(mContext)) {
			mScript.append("wipe data" + NEW_LINE);
		}
		if (Preferences.getWipeCache(mContext)) {
			mScript.append("wipe cache" + NEW_LINE);
		}
		if (Preferences.getWipeDalvik(mContext)) {
			mScript.append("wipe dalvik" + NEW_LINE);
		}

		mScript.append("install " + "/sdcard/Download/" +  mFilename + NEW_LINE);

		if (Preferences.getDeleteAfterInstall(mContext)) {
			mScript.append("cmd rm -rf " + "/sdcard/Download/" +  mFilename + NEW_LINE);
		}

		mScriptOutput = mScript.toString();
	}

	@Override
	protected Boolean doInBackground(Void... params) {

		try {
			Process p = Runtime.getRuntime().exec("sh");
			OutputStream os = p.getOutputStream();
			os.write("mkdir -p /cache/recovery/\n".getBytes());
			String cmd = "echo \"" + mScriptOutput + "\" > " + SCRIPT_FILE + "\n";
			os.write(cmd.getBytes());
			os.flush();
		} catch (Exception e) {
			Log.e(TAG, "Writing to cache" + "' error: " + e.getMessage());
			Tools.shell("echo \"" + mScriptOutput + "\" > " + SCRIPT_FILE, true);
		}

		return true;
	}
	@Override
	protected void onPostExecute(Boolean value) {
		mLoadingDialog.cancel();
		Tools.recovery(mContext);
	}
}