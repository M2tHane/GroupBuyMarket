package cn.bugstack.infrastructure.dcc;

import cn.bugstack.types.annotations.DCCValue;
import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @description 动态配置服务
 * @create 2025-01-03 15:38
 */
@Service
public class DCCService {

    /**
     * 降级开关 0关闭、1开启
     */
    @DCCValue("downgradeSwitch:0")
    private String downgradeSwitch;

    @DCCValue("cutRange:100")
    private String cutRange;

    public boolean isDowngradeSwitch() {
        return "1".equals(downgradeSwitch);
    }

    public boolean isCutRange(String userId) {
        int hashCode = Math.abs(userId.hashCode());
        int lastTwoDigits = hashCode % 100;
        return lastTwoDigits <= Integer.parseInt(cutRange);
    }

}