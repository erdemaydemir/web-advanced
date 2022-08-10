package com.forguta.libs.web.core.model.request;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Size;

@Data
public class PageableRequest {

    public int page;

    @Size(min = 1, max = 100)
    public int pageSize;

    public SortRequest sort;

    public PageRequest to() {
        if (sort != null) {
            Sort sort = Sort.by(Sort.Direction.fromString(this.sort.getDirection().name()), this.sort.getProperty());
            return PageRequest.of(getPage(), getPageSize(), sort);
        }
        return PageRequest.of(getPage(), getPageSize());
    }

    @Data
    public static class SortRequest {
        private String property;
        private DirectionEnum direction;
    }

    public static enum DirectionEnum {
        ASC, DESC;
    }
}
