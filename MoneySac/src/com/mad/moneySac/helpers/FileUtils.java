package com.mad.moneySac.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.os.Environment;

import com.mad.moneySac.model.DatabaseHelper;

public class FileUtils {
	/**
	 * Creates the specified <code>toFile</code> as a byte for byte copy of the
	 * <code>fromFile</code>. If <code>toFile</code> already exists, then it
	 * will be replaced with a copy of <code>fromFile</code>. The name and path
	 * of <code>toFile</code> will be that of <code>toFile</code>.<br/>
	 * <br/>
	 * <i> Note: <code>fromFile</code> and <code>toFile</code> will be closed by
	 * this function.</i>
	 * 
	 * @param fromFile
	 *            - FileInputStream for the file to copy from.
	 * @param toFile
	 *            - FileInputStream for the file to copy to.
	 */
	private static void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
		FileChannel fromChannel = null;
		FileChannel toChannel = null;
		try {
			fromChannel = fromFile.getChannel();
			toChannel = toFile.getChannel();
			fromChannel.transferTo(0, fromChannel.size(), toChannel);
		} finally {
			try {
				if (fromChannel != null) {
					fromChannel.close();
				}
			} finally {
				if (toChannel != null) {
					toChannel.close();
				}
			}
		}
	}

	public static String exportDB(Context context) {
		File sd = new File(Environment.getExternalStorageDirectory().toString() + "/MoneySacExport");
		sd.mkdirs();
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd'_'HH:mm:ss");
		String time = sdf.format(cal.getTime());
		FileChannel source = null;
		FileChannel destination = null;
		String currentDBPath = context.getApplicationInfo().dataDir + "/databases/" + DatabaseHelper.DATABASE_NAME;
		String backupDBPath = time + "_" + DatabaseHelper.DATABASE_NAME;
		File currentDB = new File(currentDBPath);
		File backupDB = new File(sd, backupDBPath);
		try {
			source = new FileInputStream(currentDB).getChannel();
			destination = new FileOutputStream(backupDB).getChannel();
			destination.transferFrom(source, 0, source.size());
			source.close();
			destination.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return backupDB.toString();
	}

	/**
	 * Copies the database file at the specified location over the current
	 * internal application database.
	 */
	public static void importDatabase(Context context, File dbToImport) throws IOException {

		File oldDb = new File(context.getApplicationInfo().dataDir + "/databases/" + DatabaseHelper.DATABASE_NAME);
		FileUtils.copyFile(new FileInputStream(dbToImport), new FileOutputStream(oldDb));

	}
}
