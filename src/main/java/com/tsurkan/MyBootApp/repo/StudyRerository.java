package com.tsurkan.MyBootApp.repo;

import com.tsurkan.MyBootApp.domain.study.Portion;
import com.tsurkan.MyBootApp.domain.study.Topic;
import org.springframework.data.repository.CrudRepository;


public interface StudyRerository extends CrudRepository<Portion, Long> {

}
