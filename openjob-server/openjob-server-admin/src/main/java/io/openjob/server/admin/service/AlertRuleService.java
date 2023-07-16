package io.openjob.server.admin.service;

import io.openjob.server.admin.request.alert.AddAlertRuleRequest;
import io.openjob.server.admin.request.alert.DeleteAlertRuleRequest;
import io.openjob.server.admin.request.alert.ListAlertRuleRequest;
import io.openjob.server.admin.request.alert.UpdateAlertRuleRequest;
import io.openjob.server.admin.request.alert.UpdateAlertRuleStatusRequest;
import io.openjob.server.admin.vo.alert.AddAlertRuleVO;
import io.openjob.server.admin.vo.alert.DeleteAlertRuleVO;
import io.openjob.server.admin.vo.alert.ListAlertRuleVO;
import io.openjob.server.admin.vo.alert.UpdateAlertRuleStatusVO;
import io.openjob.server.admin.vo.alert.UpdateAlertRuleVO;
import io.openjob.server.common.vo.PageVO;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.5
 */
public interface AlertRuleService {

    /**
     * Add alert rule
     *
     * @param request request
     * @return ListAlertRuleVO
     */
    AddAlertRuleVO add(AddAlertRuleRequest request);

    /**
     * Delete alert rule
     *
     * @param request request
     * @return ListAlertRuleVO
     */
    DeleteAlertRuleVO delete(DeleteAlertRuleRequest request);

    /**
     * Update alert rule
     *
     * @param request request
     * @return ListAlertRuleVO
     */
    UpdateAlertRuleVO update(UpdateAlertRuleRequest request);

    /**
     * Update alert rule status
     *
     * @param request request
     * @return ListAlertRuleVO
     */
    UpdateAlertRuleStatusVO updateStatus(UpdateAlertRuleStatusRequest request);

    /**
     * List alert rule
     *
     * @param request request
     * @return PageVO
     */
    PageVO<ListAlertRuleVO> list(ListAlertRuleRequest request);
}
