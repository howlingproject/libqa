package com.libqa.web.repository;

import com.libqa.web.domain.QaContent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by yong on 15. 2. 1..
 */
public interface QaContentRepository extends JpaRepository<QaContent, Integer> {
    QaContent findOneByQaIdAndIsDeleted(Integer qaId, boolean isDeleted);

    List<QaContent> findAllByQaIdInAndIsReplyedAndUpdateDateBetweenAndIsDeletedOrderByUpdateDateDesc(List<Integer> qaIds, boolean isReplyed, Date fromDate, Date today, boolean isDeleted);

    List<QaContent> findAllByQaIdInAndUpdateDateBetweenAndIsDeletedOrderByUpdateDateDesc(List<Integer> qaIds, Date fromDate, Date today, boolean isDeleted);

    List<QaContent> findAllByQaIdInAndIsReplyedAndIsDeletedOrderByUpdateDateDesc(List<Integer> qaIds, boolean isReplyed, boolean isDeleted);

    List<QaContent> findByUserIdAndIsDeleted(Integer userId, boolean isDeleted);

    List<QaContent> findByQaIdInAndIsDeletedOrderByQaIdDesc(List<Integer> qaIds, boolean isDeleted);

    List<QaContent> findByIsDeletedFalse(Pageable pageable);

    Integer countByIsDeletedFalse();

    Integer countByIsReplyedFalseAndIsDeletedFalse();

    QaContent findByQaIdAndIsDeletedFalse(Integer qaId);

	List<QaContent> findByIsDeletedFalseAndIsReplyedFalse(Pageable Pageable);

	List<QaContent> findByUpdateDateBetweenAndIsDeletedFalse(Date fromDate, Date today, Pageable Pageable);

	Stream<QaContent> findAllByUpdateDateBetweenAndIsDeletedFalseOrderByUpdateDateDesc(Date fromDate, Date today);

	Stream<QaContent> findAllByIsReplyedAndUpdateDateBetweenAndIsDeletedFalseOrderByUpdateDateDesc(boolean isReplyed, Date fromDate, Date today);

	Stream<QaContent> findAllByIsReplyedAndIsDeletedFalseOrderByUpdateDateDesc(boolean isReplyed);

	@Query(value = "select qa.* " +
			" from QA_CONTENT qa, KEYWORD keyword " +
			"where qa.qa_id = keyword.qa_id " +
			"  and qa.is_deleted = 0 " +
			"  and qa.is_replyed = :isReplyed " +
			"  and keyword.is_deleted = 0 " +
			"  and keyword.keyword_type = :keywordType " +
			"  and keyword.keyword_name = :keywordName " +
			"group by qa.user_id, qa.qa_id, qa.insert_date, qa.update_date, qa.user_nick, qa.title, qa.contents, qa.contents_markup, qa.view_count, qa.recommend_count " +
			"order by qa.update_date desc", nativeQuery = true)
	Stream<QaContent> findAllByKeywordAndIsReplyedAndDayTypeAndIsDeletedFalse(@Param("keywordType") String keywordType, @Param("keywordName") String keywordName, @Param("isReplyed") boolean isReplyed);

	@Query(value = "select qa.* " +
			" from QA_CONTENT qa, KEYWORD keyword " +
			"where qa.qa_id = keyword.qa_id " +
			"  and qa.is_deleted = 0 " +
			"  and qa.update_date between :fromDate and :today " +
			"  and qa.is_replyed = :isReplyed " +
			"  and keyword.is_deleted = 0 " +
			"  and keyword.keyword_type = :keywordType " +
			"  and keyword.keyword_name = :keywordName " +
			"group by qa.user_id, qa.qa_id, qa.insert_date, qa.update_date, qa.user_nick, qa.title, qa.contents, qa.contents_markup, qa.view_count, qa.recommend_count " +
			"order by qa.update_date desc", nativeQuery = true)
	Stream<QaContent> findAllByKeywordAndIsReplyedAndDayTypeAndIsDeletedFalseAndUpdateDateBetween(@Param("keywordType") String keywordType, @Param("keywordName") String keywordName, @Param("isReplyed") boolean isReplyed, @Param("fromDate") Date fromDate, @Param("today") Date today);

	@Query(value = "select qa.* " +
			" from QA_CONTENT qa, KEYWORD keyword " +
			"where qa.qa_id = keyword.qa_id " +
			"  and qa.is_deleted = 0 " +
			"  and qa.update_date between :fromDate and :today " +
			"  and keyword.is_deleted = 0 " +
			"  and keyword.keyword_type = :keywordType " +
			"  and keyword.keyword_name = :keywordName " +
			"group by qa.user_id, qa.qa_id, qa.insert_date, qa.update_date, qa.user_nick, qa.title, qa.contents, qa.contents_markup, qa.view_count, qa.recommend_count " +
			"order by qa.update_date desc", nativeQuery = true)
	Stream<QaContent> findAllByKeywordAndDayTypeAndIsDeletedFalse(@Param("keywordType") String keywordType, @Param("keywordName") String keywordName,  @Param("fromDate") Date fromDate, @Param("today") Date today);

}
