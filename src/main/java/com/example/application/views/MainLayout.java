package com.example.application.views;

import com.example.application.views.account.AccountView;
import com.example.application.views.homepage.HomeView;
import com.example.application.views.plan.PlanView;
import com.example.application.views.settings.SettingsView;

import com.example.application.views.workout.WorkoutView;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tab;
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
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import org.vaadin.lineawesome.LineAwesomeIcon;
import com.vaadin.flow.router.RouterLayout;

import java.util.Locale;

/**
 * The main layout is a top-level placeholder for other views.
 */
@Layout
@AnonymousAllowed
public class MainLayout extends VerticalLayout implements RouterLayout {

    private Div content;
    private HasElement contentView;

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
        Tab en = new Tab("Englisch");

        de.setId(Locale.GERMAN.toLanguageTag());
        en.setId(Locale.ENGLISH.toLanguageTag());

        tabsLanguage.add(de, en);

        tabsLanguage.addSelectedChangeListener(e -> {
            var tabsId = e.getSelectedTab().getId().orElse(Locale.ENGLISH.toLanguageTag());
            var language = getTabsLocale(tabsId);
            switchLanguage(language);
        });

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
        StreamResource iconResource = new StreamResource("barbell.svg",
                () -> getClass().getResourceAsStream("/icons/barbell.svg"));
        SvgIcon icon = new SvgIcon(iconResource);

        return new MenuItemInfo[]{
                new MenuItemInfo("Home", LineAwesomeIcon.HOME_SOLID.create(), HomeView.class),
                new MenuItemInfo("Workout", icon, WorkoutView.class),
                new MenuItemInfo("Plan", LineAwesomeIcon.LIST_UL_SOLID.create(), PlanView.class),
                new MenuItemInfo("Settings", LineAwesomeIcon.COG_SOLID.create(), SettingsView.class),
                new MenuItemInfo("Account", LineAwesomeIcon.USER.create(), AccountView.class),
        };
    }

    private Locale getTabsLocale(String lang) {
        return Locale.forLanguageTag(lang);
    }

    public void switchLanguage(Locale locale) {
        // Set the new locale
        getUI().ifPresent(ui -> {
            ui.setLocale(locale);
            // Refresh the UI components to reflect the new locale
            ui.access(this::updateTexts);
        });
    }

    private void updateTexts() {
        // Update other components as needed
        if (this.contentView == null) {
            return;
        }

        if (this.contentView instanceof HomeView) {
            ((HomeView) this.contentView).updateTexts();
        }
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        this.contentView = content;

        RouterLayout.super.showRouterLayoutContent(content);
        this.content.removeAll();
        this.content.getElement().appendChild(content.getElement());
    }

    public record DataEntity(String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String sunday) {}
}
