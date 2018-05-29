package com.spring.smart_plant.device.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.smart_plant.common.domain.ResultDTO;
import com.spring.smart_plant.common.utills.ConstantJwtService;
import com.spring.smart_plant.device.dao.DeviceDAO;
import com.spring.smart_plant.device.domain.APInfoDTO;

@Service("addAPService")
public class AddAPServiceImpl implements IDeviceService {
	private final String PHP_SEARCH_URL = "/search.php";
	private final String PHP_ADD_URL = "/add.php";

	@Autowired
	private DeviceDAO dao;

	@SuppressWarnings("unchecked")
	@Override
	public ResultDTO execute(Object obj) {
		// TODO Auto-generated method stub
		String publicIP = (String) obj;
		UrlConnectionCommand conn = new UrlConnectionCommand();
		try {
			JSONObject json = conn.request(publicIP, PHP_SEARCH_URL, "POST", null);
			System.out.println(json.get("state"));
			if (json.get("state").equals("OK")) {// 사용가능한 공유기인 경우
				System.out.println("insert");
				// JWT로 부터 usercode를 알아냄
				int userCode = (int) ConstantJwtService.getJwtService().get("member").get("userCode");

				// add.php로 usercode를 전송
				StringBuilder sb = new StringBuilder();
				//pre
				sb.append("{\"user_code\":\"").append(userCode).append("\",\"submit_ip\":\"").append(publicIP)
						.append("\",\"sf_code\":[");
				dao.insertAP(new APInfoDTO(publicIP, (String) json.get("ssid"), userCode));
				//append sfCode each ip
				JSONArray arr = (JSONArray) json.get("inner_ip");
				if (arr != null) {
					List<Object> list = arr.toList();// 연결된 수경재배기 IP들을 가져옴
					int count=0;
					for (Object innerIp : list) {// 수경재배기들을 등록
						try {
							HashMap<String, Object> map=(HashMap<String, Object>)innerIp;
							int sfCode=dao.insertSmartFarmDevice(map.get("INNER_IP").toString(),
									userCode, publicIP);
							sb.append("{\"ip\":\"").append(map.get("INNER_IP"))
							.append("\",\"code\":\"").append(sfCode).append("\"}");
							if(list.size()-1!=count)
								sb.append(",");
							count++;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							return ResultDTO.createInstance(false).setMsg("등록 오류입니다.");
						}
					}
				}
				//post
				sb.append("]}");
				//데이터 전송
				System.out.println(sb);
				conn.request(publicIP, PHP_ADD_URL, "POST", sb.toString());//.get("result");
				return ResultDTO.createInstance(true).setMsg("정상적으로 등록되었습니다.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultDTO.createInstance(false).setMsg("등록 오류입니다.");
	}
}