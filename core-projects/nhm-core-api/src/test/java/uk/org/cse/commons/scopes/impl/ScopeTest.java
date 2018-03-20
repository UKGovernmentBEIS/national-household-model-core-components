package uk.org.cse.commons.scopes.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import uk.org.cse.commons.scopes.IScopeVisitor;

public class ScopeTest {
	@Test
	public void localNotesAreAssignable() {
		final Scope<String> root = Scope.root("top");
		
		root.addNote(1);
		root.addNote(4.0);
		root.addNote(2);

		Assert.assertEquals("Double is not assignable to integer, so we should get just 1 and 2",
							ImmutableList.of(1, 2),
							root.getLocalNotes(Integer.class));

		Assert.assertEquals("Everything is assignable to number",
							ImmutableList.of(1, 4.0, 2),
							root.getLocalNotes(Number.class));

		Assert.assertEquals("Only 4.0 is a double",
							ImmutableList.of(4.0),
							root.getLocalNotes(Double.class));
	}

	@Test
	public void localNotesAreOnlyLocalWhereasGlobalNotesAreGlobal() {
		final Scope<String> root = Scope.root("top");
		root.addNote(1);
		final Scope<String> child = root.child("sub");
		child.addNote(2);
		child.close();
		root.addNote(3);

		Assert.assertEquals("Root's local notes don't contain sub's local notes",
							ImmutableList.of(1, 3),
							root.getLocalNotes(Integer.class));

		Assert.assertEquals("Getting all notes includes child's note",
							ImmutableList.of(1, 2, 3),
							root.getAllNotes(Integer.class));
	}

	@Test
	public void globalNotesMustBeAssignableToAppear() {
		final Scope<String> root = Scope.root("top");
		root.addNote(1);
		root.addNote(2.0);
		final Scope<String> child = root.child("sub");
		child.addNote(3);
		child.addNote(4.0);
		final Scope<String> down = child.child("sub2");
		down.addNote(5);
		down.addNote(6.0);
		down.close();
		child.addNote(7);
		child.close();
		root.addNote(8);
		root.close();
		
		Assert.assertEquals("Only integers should be yielded here",
							ImmutableList.of(1, 3, 5, 7, 8),
							root.getAllNotes(Integer.class));

		Assert.assertEquals("Only integers should be yielded here",
							ImmutableList.of(3, 5, 7),
							child.getAllNotes(Integer.class));

		Assert.assertEquals("Only integers should be yielded here",
							ImmutableList.of(5),
							down.getAllNotes(Integer.class));
	}

	@Test(expected=IllegalStateException.class)
	public void parentCannotBeAddedToIfChildIsOpen() {
		final Scope<String> root = Scope.root("top");
		root.child("sub");
		root.addNote(1);
	}

	@Test(expected=IllegalStateException.class)
	public void multipleChildrenCannotBeOpenAtOnce() {
		final Scope<String> root = Scope.root("top");
		root.child("sub");
		root.child("sub2");
	}

	@Test(expected=IllegalStateException.class)
	public void closedScopeCannotBeClosed() {
		final Scope<String> root = Scope.root("top");
		root.close();
		root.close();
	}

	@Test(expected=IllegalStateException.class)
	public void parentCannotBeClosedIfChildIsOpen() {
		final Scope<String> root = Scope.root("top");
		root.child("sub");
		root.close();
	}
	
	@Test
	public void nearestIsAbsentOrPresentAccordingly() {
		final Scope<String> root = Scope.root("top");
		root.addNote(1);
		Assert.assertFalse("Nothing matching is there", root.getNearestNote(Double.class).isPresent());
		Assert.assertTrue("Nothing matching is there", root.getNearestNote(Integer.class).isPresent());
	}
	
	@Test
	public void nearestGivesNearest() {
		final Scope<String> root = Scope.root("top");
		root.addNote(1);
		
		Scope<String> child = root.child("sub");
		child.addNote(9.0);
		child.addNote("Hello");
		child.close();
		
		Scope<String> child2 = root.child("sub");
		child2.addNote(4.0);
		child2.addNote("World");
		child2.close();
		
		root.addNote(3.9);
		root.close();
		
		Assert.assertEquals("Should get the highest level answer out", Optional.of(3.9), root.getNearestNote(Double.class));
		Assert.assertEquals("Should get the rightmost answer out", Optional.of("World"), root.getNearestNote(String.class));
	}

	@Test
	public void getLocalNoteShouldReturnNoteIfExactlyOneElseAbsent() {
		final Scope<String> root = Scope.root("top");
		Assert.assertEquals("Should not return notes if there are none.", Optional.absent(), root.getLocalNote(Object.class));
		
		Object note = new Object();
		root.addNote(note);
		Assert.assertEquals("Should return a note if there is exactly one.", Optional.of(note), root.getLocalNote(Object.class));
		
		root.addNote(note);
		Assert.assertEquals("Should not return notes if there are many.", Optional.absent(), root.getLocalNote(Object.class));
	}
	
	@Test
	public void visitorVisitsAsExpected() {
		final Scope<String> root = Scope.root("top");
		
		root.addNote(1d);
		root.addNote(2);
		Scope<String> child = root.child("hello");
		child.addNote(3);
		child.close();
		root.addNote(4);
		Scope<String> child2 = root.child("world");
		child2.addNote("asdf");
		child2.close();
		root.addNote(9);
		
		final List<Object> objects = new ArrayList<>();
		final List<String> scopes = new ArrayList<>();
		
		root.accept(new IScopeVisitor<String>() {
			Stack<String> stack = new Stack<>();
			@Override
			public void enterScope(String tag) {
				stack.push(tag);
			}

			@Override
			public void visit(Object note) {
				objects.add(note);
				scopes.add(stack.peek());
			}

			@Override
			public void exitScope() {
				stack.pop();
			}
		});
		
		Assert.assertEquals(
				ImmutableList.of(1d, 2, 3, 4, "asdf", 9),
				objects
				);
		
		Assert.assertEquals(ImmutableList.of("top", "top", "hello", "top", "world", "top"),
				scopes);
	}
}
