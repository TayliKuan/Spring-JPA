package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer>{//第二個參數是PK的類型
	
	//自訂義方法
	//效果 SELECT * FROM Student WHERE name =?
	List<Student> findByName(String name);
	
	//效果 SELECT * FROM Student WHERE id = ? AND name = ?
	Student findByIdAndName(Integer id,String name);//參數順序很重要!
	
	@Query(value="SELECT id,name FROM student WHERE id=?1 AND name =?2",nativeQuery = true)
	Student testQuery(Integer id,String name);
}
