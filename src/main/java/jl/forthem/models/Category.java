package jl.forthem.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tdl_category")
public class Category implements Comparable {	
	
	@Id
	@Column(name="CATEGORY_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="CATEGORY_NAME")
	private String name;
	
	@OneToMany(targetEntity = Item.class, mappedBy = "categoryId", orphanRemoval = false)	
	private List<Item> items;

	protected Category() {
		super();
	}
	
	public Category(Integer id, String name, List<Item> items) {
		super();
		this.id = id;
		this.name = name;
		this.items = items;
	}	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}	

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", items=" + items + "]";
	}

	public int compareTo(Object category) {
		return this.getName().compareTo(((Category)category).getName());
		
	}
	
	

}
