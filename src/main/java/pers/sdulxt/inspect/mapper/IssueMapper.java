package pers.sdulxt.inspect.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import pers.sdulxt.inspect.entity.IssueEntity;

import java.util.List;

@Component
public interface IssueMapper {

    @Select("select * from view_issue where taskId = #{taskId} and deviceId = #{deviceId}")
    List<IssueEntity> getIssuesByTaskDevice(@Param("taskId") int taskId, @Param("deviceId") int deviceId);

    @Insert("insert into issue (`taskId`, `deviceId`, `picture`, `description`, `title`, `creator`) values (#{taskId}, #{deviceId}, #{picture}, #{description}, #{title}, #{creator})")
    @Options(useGeneratedKeys = true)
    void insertIssue(IssueEntity issueEntity);

}
