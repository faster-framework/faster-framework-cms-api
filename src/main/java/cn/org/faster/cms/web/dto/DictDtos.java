package cn.org.faster.cms.web.dto;

import cn.org.faster.cms.domain.SysDict;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 字典请求体。
 */
public final class DictDtos {

    private DictDtos() {
    }

    public record Create(
            @NotBlank(message = "字典名称不能为空") @Size(max = 128) String name,
            @NotBlank(message = "字典类型不能为空") @Size(max = 128) String type,
            @NotBlank(message = "字典值不能为空") String dictValue,
            Integer showStatus,
            Integer sort,
            @Size(max = 1024) String remark
    ) {

        public SysDict toEntity() {
            SysDict dict = new SysDict();
            dict.setName(name);
            dict.setType(type);
            dict.setDictValue(dictValue);
            dict.setShowStatus(showStatus);
            dict.setSort(sort);
            dict.setRemark(remark);
            return dict;
        }
    }

    public record Update(
            @Size(max = 128) String name,
            @Size(max = 128) String type,
            String dictValue,
            Integer showStatus,
            Integer sort,
            @Size(max = 1024) String remark
    ) {

        public SysDict toEntity() {
            SysDict dict = new SysDict();
            dict.setName(name);
            dict.setType(type);
            dict.setDictValue(dictValue);
            dict.setShowStatus(showStatus);
            dict.setSort(sort);
            dict.setRemark(remark);
            return dict;
        }
    }
}
