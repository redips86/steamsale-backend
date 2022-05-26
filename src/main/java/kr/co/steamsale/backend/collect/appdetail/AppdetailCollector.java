package kr.co.steamsale.backend.collect.appdetail;

import java.util.List;

import kr.co.steamsale.backend.data.AppDetailWlist;
import kr.co.steamsale.backend.service.CollectService;

import com.prismweaver.backend.sync.AbstractMultiThreadSync;

public class AppdetailCollector extends AbstractMultiThreadSync<AppDetailWlist> {

	private static final String programId = "SS_COL_005";

	public AppdetailCollector() {
		super(programId);
		this.setRunner(AppdetailCollectorHandler.class);
	}

	public static void main(String[] args) {
		new AppdetailCollector().execute();
	}

	@Override
	protected List<AppDetailWlist> selectSyncItem(int selectCount, long lastSyncId) {
		return new CollectService().getAppDetailWlist(selectCount);
	}

}
