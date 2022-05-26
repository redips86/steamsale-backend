package kr.co.steamsale.backend.collect.appdetail;

import java.util.List;

import kr.co.steamsale.backend.data.AppDetailWlist;

import com.prismweaver.backend.sync.AbstractMultiProcessRunner;

public class AppdetailCollectorHandler extends AbstractMultiProcessRunner<AppDetailWlist>{

	public AppdetailCollectorHandler(List<AppDetailWlist> dataList, String oprt) {
		super(dataList, oprt);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(AppDetailWlist data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int updateStatus(AppDetailWlist data, String status, String preStatus, String oprt) {
		// TODO Auto-generated method stub
		return 0;
	}

}
