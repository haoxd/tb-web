package com.tb.web.service.user;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.common.bean.api.RespInfo;
import com.tb.web.entity.TbUser;
import com.tb.web.sys.contenst.httpContenst.HttpConstants.HttpStatusCode;
import com.tb.web.sys.httplient.ws.server.intf.HttpClientApiServerTools;

@Service("tbUserService")
public class TbUserService {

	/**
	 * http 服务
	 */
	@Resource(name = "apiServer")
	private HttpClientApiServerTools apiServer;

	private static final ObjectMapper oMapper = new ObjectMapper();
	
	@Value("${TB_SSO_TOKEN_URL}")
	public String TB_SSO_TOKEN_URL;

	public TbUser queryUserByToken(String token) {

		RespInfo resp;
		try {
			resp = this.apiServer.sendGetNoReadXML(TB_SSO_TOKEN_URL + token);

			if (HttpStatusCode.OK.equals(resp.getRespCode())) {
				String jsonData = (String) resp.getData();
				if (StringUtils.isEmpty(jsonData)) {
					return null;
				}
				return oMapper.readValue(jsonData, TbUser.class);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
