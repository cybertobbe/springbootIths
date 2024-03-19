package com.example.springbootprojektiths.repository;import com.example.springbootprojektiths.entity.Message;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MessageRepository extends ListCrudRepository<Message, Long> {
    @Query("""
            update Message m set m.visible = true where m.id = ?1
            """)
    @Modifying
    @Transactional
    int setVisible(Long id);
}
