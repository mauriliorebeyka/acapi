package com.rebeyka.acapi.actionables;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.entities.gameflow.Play;
import com.rebeyka.acapi.exceptions.ActionableCopyException;

public class ActionableTest {

	static class TestActionable extends Actionable {

		String field1;
		
		String field2;
		
		public TestActionable() {
			super("");
		}

		@Override
		public void execute() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void rollback() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getMessage() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	@Test
	public void testCopy() {
		TestActionable actionable = new TestActionable();
		Play play = mock(Play.class);
		actionable.field1 = "TEST";
		actionable.field2 = "ANOTHER TEST";
		TestActionable copy = (TestActionable)actionable.copy(play);
		copy.field2 = "MODIFIED";
		assertThat(copy).isNotSameAs(actionable);
		assertThat(actionable).extracting("field1","field2").containsExactly("TEST", "ANOTHER TEST");
		assertThat(actionable.getParent()).isNull();
		assertThat(copy).extracting("field1","field2").containsExactly("TEST", "MODIFIED");
		assertThat(copy.getParent()).isEqualTo(play);
	}
	
	@Test
	public void testUnableToCopy() throws CloneNotSupportedException {
		TestActionable actionable = spy(new TestActionable());
		doThrow(new CloneNotSupportedException()).when(actionable).doClone();
		assertThrows(ActionableCopyException.class, () -> actionable.copy(null));
	}
}
