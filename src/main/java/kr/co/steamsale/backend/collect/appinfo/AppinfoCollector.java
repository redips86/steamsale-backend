package kr.co.steamsale.backend.collect.appinfo;

import java.util.List;

import kr.co.steamsale.backend.data.AppWlist;
import kr.co.steamsale.backend.service.CollectService;

import com.prismweaver.backend.sync.AbstractMultiThreadSync;

public class AppinfoCollector extends AbstractMultiThreadSync<AppWlist> {

	private static final String programId = "SS_COL_003";

	public AppinfoCollector() {
		super(programId);
		this.setRunner(AppinfoCollectorHandler.class);
	}

	public static void main(String[] args) {
		new AppinfoCollector().execute();
	}

	@Override
	protected List<AppWlist> selectSyncItem(int selectCount, long lastSyncId) {
		return new CollectService().getAppWlist(selectCount);
	}

}
