package com.vinsguru.agent.mcp;

import com.vinsguru.agent.dto.ToolResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AnalyticsTools {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsTools.class);
    private final JdbcTemplate template;

    public AnalyticsTools(JdbcTemplate template) {
        this.template = template;
    }

    @McpTool(name = "execute-query", description = "Executes the given SQL query and provides the results")
    public ToolResult<List<Map<String, Object>>> executeQuery(String sql) {
        try{
            log.info("sql: {}", sql);
            return ToolResult.success(
                    this.template.queryForList(sql)
            );
        }catch (Exception e){
            log.error("SQL error", e);
            return ToolResult.failure(e);
        }
    }

}
