package com.libqa.web.view.space;

import com.libqa.web.domain.Space;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author : yion
 * @Date : 2016. 3. 27.
 * @Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceMainList {
    private Integer currentPage;
    private Integer totalPages;
    private Long totalElements;
    private List<SpaceMain> spaceMainList;
}
