package cse.nhm.ide.ui.editor.hover;

import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextHoverExtension;
import org.eclipse.jface.text.ITextHoverExtension2;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;

import uk.org.cse.nhm.bundle.api.IDefinition;
import uk.org.cse.nhm.bundle.api.IDefinition.DefinitionType;
import uk.org.cse.nhm.bundle.api.INationalHouseholdModel;

import com.google.common.base.CharMatcher;
import com.google.common.base.Optional;

import cse.nhm.ide.ui.editor.ScenarioEditor;
import cse.nhm.ide.ui.reader.Cursor;

public class HelpTextHover implements ITextHover, ITextHoverExtension, ITextHoverExtension2 {
	private final ScenarioEditor editor;
	final INationalHouseholdModel model;
	
	public HelpTextHover(final ScenarioEditor editor, final INationalHouseholdModel model) {
		super();
		this.editor = editor;
		this.model = model;
	}

	final CharMatcher breaks = CharMatcher.anyOf("()[]{} \t\n;\"");
	private IInformationControlCreator informationControlCreator = ModelInformationControlCreator.NORMAL;
	
	@Override
	public IInformationControlCreator getHoverControlCreator() {
		return informationControlCreator ;
	}
	
	@Override
	public Object getHoverInfo2(final ITextViewer textViewer, final IRegion hoverRegion) {
		//if (true) return "hello";
		final Cursor c = Cursor.get(textViewer.getDocument().get(), hoverRegion.getOffset() + hoverRegion.getLength());
		final Optional<String> descr = this.model.language().describe(c);

		if (descr.isPresent()) {
			return new ManualPage(c, descr.get());
		} else {
			// try and find a definition in this project
			final Set<? extends IDefinition<IPath>> definitions = 
					editor.getDefinitions(new NullProgressMonitor());
			
			if (!c.command().isEmpty()) {
				for (final IDefinition<IPath> p : definitions) {
					if (p.name().equals(c.command()) && p.type() == DefinitionType.Template) {
						return new UserTemplate(c, p);
					}
				}
				if (c.module().isPresent()) {
					final String relName = c.module().get() + "/" + c.command();
					for (final IDefinition<IPath> p : definitions) {
						if (p.name().equals(relName) && p.type() == DefinitionType.Template) {
							return new UserTemplate(c, p);
						}
					}
				}
			}
			
		}
		
		return null;
	}

	@Override
	public String getHoverInfo(final ITextViewer textViewer, final IRegion hoverRegion) {return "woo";}

	@Override
	public IRegion getHoverRegion(final ITextViewer textViewer, final int offset) {
		final String s = textViewer.getDocument().get();
		int leftOffset = offset;
		int rightOffset = offset;
		while (!this.breaks.matches(s.charAt(leftOffset))) {
			if (s.charAt(leftOffset) == ':') {
				leftOffset++;
				break;
			}
			leftOffset--;
			if (leftOffset == 0) break;
		}
		while (!this.breaks.matches(s.charAt(rightOffset))) {
			rightOffset++;
			if (s.charAt(rightOffset) == ':') {
				rightOffset++;
				break;
			}
			if (rightOffset == s.length()-1) break;
		}
		
		return new Region(leftOffset, rightOffset - leftOffset);
	}
	
}
