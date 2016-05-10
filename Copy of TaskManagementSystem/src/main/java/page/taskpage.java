package page;

import java.util.Iterator;
import java.util.List;

import jsonview.UserView;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.annotation.JsonView;

public class taskpage<T> implements Page<T>{

	
		private Page<T> pageObj;

		/**
		 * Constructor to set the page object
		 * 
		 * @param pageObj
		 */
		public taskpage(Page<T> pageObj) {
			this.pageObj = pageObj;
		}

		@JsonView(UserView.user.class)
		@Override
		public int getNumber() {
			return pageObj.getNumber();
		}

		@Override
		public int getSize() {
			return pageObj.getSize();
		}

		@Override
		public int getNumberOfElements() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public List<T> getContent() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasContent() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Sort getSort() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isFirst() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isLast() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean hasPrevious() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Pageable nextPageable() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Pageable previousPageable() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Iterator<T> iterator() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getTotalPages() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getTotalElements() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public <S> Page<S> map(Converter<? super T, ? extends S> converter) {
			// TODO Auto-generated method stub
			return null;
		}

	
}
