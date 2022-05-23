package ti.mall.api.admin;

import io.swagger.annotations.Api;
import ti.mall.api.admin.param.AdminLoginParam;
import ti.mall.common.Constants;
import ti.mall.service.AdminUserService;
import ti.mall.util.Result;
import ti.mall.util.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@Api(value = "v1", tags = "后台管理系统管理员模块接口")
@RequestMapping("/manage-api/v1")
public class NewBeeAdminManageUserAPI {
    @Resource
    private AdminUserService adminUserService;

    private static final Logger logger = LoggerFactory.getLogger(NewBeeAdminManageUserAPI.class);

    @RequestMapping(value = "/adminUser/login", method = RequestMethod.POST)
    public Result<String> login(@RequestBody @Valid AdminLoginParam adminLoginParam) {
        String loginResult = adminUserService.login(adminLoginParam.getUserName(), adminLoginParam.getPasswordMd5());
        logger.info("manage login api,adminName={},loginResult={}", adminLoginParam.getUserName(), loginResult);

        //登录成功
        if (!StringUtils.isEmpty(loginResult) && loginResult.length() == Constants.TOKEN_LENGTH) {
            Result result = ResultGenerator.genSuccessResult();
            result.setData(loginResult);
            return result;
        }
        //登录失败
        return ResultGenerator.genFailResult(loginResult);
    }
}
