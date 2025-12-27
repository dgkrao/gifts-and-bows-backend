package com.giftsandbows.model;

import jakarta.persistence.*;

@Entity
public class SiteSettings {

    @Id
    private Long id = 1L;

    private String logoUrl;
    private String navbarColor;
    private String footerColor;
    private String footerText;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public String getNavbarColor() { return navbarColor; }
    public void setNavbarColor(String navbarColor) { this.navbarColor = navbarColor; }

    public String getFooterColor() { return footerColor; }
    public void setFooterColor(String footerColor) { this.footerColor = footerColor; }

    public String getFooterText() { return footerText; }
    public void setFooterText(String footerText) { this.footerText = footerText; }
}
