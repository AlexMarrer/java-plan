package com.example.application.views;

import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.tabs.Tabs;

import com.vaadin.flow.component.tabs.Tab;

import com.vaadin.flow.component.avatar.AvatarGroup;

import com.vaadin.flow.component.avatar.AvatarGroup.AvatarGroupItem;

import com.example.application.views.about.AboutView;
import com.example.application.views.helloworld.HelloWorldView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.*;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.BoxSizing;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Height;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.theme.lumo.LumoUtility.Whitespace;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;
import org.vaadin.lineawesome.LineAwesomeIcon;
import com.vaadin.flow.router.RouterLayout;

import java.util.Collection;

/**
 * The main view is a top-level placeholder for other views.
 */
@Layout
@AnonymousAllowed
public class MainLayout extends VerticalLayout implements RouterLayout {

    private Div content;

    /**
     * A simple navigation item component, based on ListItem element.
     */
    public static class MenuItemInfo extends ListItem {

        private final Class<? extends Component> view;

        public MenuItemInfo(String menuTitle, Component icon, Class<? extends Component> view) {
            this.view = view;
            RouterLink link = new RouterLink();
            // Use Lumo classnames for various styling
            link.addClassNames(Display.FLEX, Gap.XSMALL, Height.MEDIUM, AlignItems.CENTER, Padding.Horizontal.SMALL,
                    TextColor.BODY);
            link.setRoute(view);

            Span text = new Span(menuTitle);
            // Use Lumo classnames for various styling
            text.addClassNames(FontWeight.MEDIUM, FontSize.MEDIUM, Whitespace.NOWRAP);

            if (icon != null) {
                link.add(icon);
            }
            link.add(text);
            add(link);
        }

        public Class<?> getView() {
            return view;
        }
    }

    public MainLayout() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setMargin(false);

        Header header = (Header) createHeaderContent();
        header.setWidthFull();

        this.content = new Div();
        this.content.setSizeFull();
        this.content.setWidthFull();
        this.content.addClassName("main-content");

        Header navbar = (Header) createFooterContent();
        navbar.setWidthFull();
        add(header, this.content, navbar);

        expand(this.content);
    }

    private Component createHeaderContent() {
        Header header = new Header();
        header.addClassNames(BoxSizing.BORDER, Display.FLEX, FlexDirection.COLUMN, Width.FULL);

        var layout = new Div();
        layout.addClassNames(Display.FLEX, AlignItems.CENTER, Padding.Horizontal.LARGE);

        H1 appName = new H1("java-plan");
        appName.addClassNames(Margin.Vertical.MEDIUM, Margin.End.AUTO, FontSize.LARGE);
        layout.add(appName);

        Tabs tabsLanguage = new Tabs();
        Tab de = new Tab("Deutsch");
        de.setId("de");
        tabsLanguage.add(de);
        Tab en = new Tab("Englisch");
        en.setId("en");
        tabsLanguage.add(en);

        layout.add(tabsLanguage);

        header.add(layout);
        header.addClassName("header");

        return header;
    }

    private Component createFooterContent() {
        Header header = new Header();
        header.addClassNames(BoxSizing.BORDER, Display.FLEX, FlexDirection.COLUMN, Width.FULL);

        Nav nav = new Nav();
        nav.addClassNames(Display.FLEX, Overflow.AUTO, Padding.Horizontal.MEDIUM, Padding.Vertical.XSMALL);

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames(Display.FLEX, Gap.SMALL, ListStyleType.NONE, Margin.NONE, Padding.NONE, Width.FULL, "navbar__list");
        nav.add(list);

        for (MenuItemInfo menuItem : createMenuItems()) {
            list.add(menuItem);
        }

        header.add(nav);
        header.addClassName("header");

        return header;
    }

    private MenuItemInfo[] createMenuItems() {
        return new MenuItemInfo[]{ //
                new MenuItemInfo("Hello World", LineAwesomeIcon.GLOBE_SOLID.create(), HelloWorldView.class), //
                new MenuItemInfo("About", LineAwesomeIcon.FILE.create(), AboutView.class), //
        };
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        RouterLayout.super.showRouterLayoutContent(content);
        this.content.removeAll();

        this.content.getElement().appendChild(content.getElement());
    }

    public record DataEntity(String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String sunday) {}
}
