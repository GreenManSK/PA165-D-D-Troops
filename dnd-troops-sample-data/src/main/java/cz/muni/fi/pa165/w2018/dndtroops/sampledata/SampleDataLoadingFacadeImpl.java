package cz.muni.fi.pa165.w2018.dndtroops.sampledata;

import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.*;
import cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race;
import cz.muni.fi.pa165.w2018.dndtroops.backend.enums.UserType;
import cz.muni.fi.pa165.w2018.dndtroops.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation for sample data loading facade
 *
 * @author Dusan Hetlerovic
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    private final static Logger logger = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Inject
    private TroopService troopService;

    @Inject
    private HeroService heroService;

    @Inject
    private RoleService roleService;

    @Inject
    private GroupService groupService;

    @Inject
    private UserService userService;


    @Override
    public void loadData() {
        Troop troop1 = troop("Dragon", "Seduce", 1000L);
        Troop troop2 = troop("Dragon", "Slay", 500L);
        Troop troop3 = troop("Dragon", "Sneak past", 200L);
        Troop troop4 = troop("Dragon", "Escape", 100L);
        Troop troop5 = troop("Brawler", "Beat up", 10L);
        Troop troop6 = troop("Brawler", "Defeat in tournament", 50L);
        Troop troop7 = troop("Brawler", "Slam a chair against", 30L);
        Troop troop8 = troop("Witch", "Steal concoction recipe", 50L);
        Troop troop9 = troop("Witch", "Get cure for hex", 40L);
        Troop troop10 = troop("Fairy", "Collect fairy dust", 70L);
        Troop troop11 = troop("Fairy", "Get cure for hex", 100L);
        Troop troop12 = troop("Gnomes", "Observe", 5L);
        Troop troop13 = troop("Lord Farquaad", "Dethrone", 2000L);
        Troop troop14 = troop("Captain Hook", "Hook up with", 50L);
        Troop troop15 = troop("Magic Mirror", "Observe", 0L);
        logger.debug("Troops loaded");

        Role role1 = role("Green Menace", "Defends swamp");
        Role role2 = role("Squire", "Is really annoying");
        Role role3 = role("Princess", "Good at getting kidnapped by dragons and saved by ogres");
        Role role4 = role("Royal Knight", "Fights in the name of Lord Farquaad");
        Role role5 = role("Duelist", "Uses rapier to perform misdeeds");
        Role role6 = role("Fairytale Creature", "Has a very low chance of being seen in real world");
        logger.debug("Roles loaded");


        Set<Role> roles = new HashSet<>();
        roles.add(role1);
        roles.add(role4);
        roles.add(role6);
        Hero hero1 = hero("Shrek", Race.ORC, roles,5000L);
        roles.clear();
        roles.add(role2);
        Hero hero2 = hero("Donkey", Race.HALFLING, roles, 50L);
        roles.clear();
        roles.add(role3);
        Hero hero3 = hero("Princess Fiona", Race.HUMAN, roles, 1000L);
        roles.clear();
        roles.add(role5);
        roles.add(role2);
        roles.add(role6);
        Hero hero4 = hero("Puss in Boots", Race.GNOME, roles, 700L);
        roles.clear();
        roles.add(role4);
        Hero hero5 = hero("Farquaad's Royal Guard", Race.HUMAN, roles, 200L);
        roles.clear();
        roles.add(role4);
        roles.add(role5);
        Hero hero6 = hero("Farquaad's Assassin", Race.DWARF, roles, 400L);
        roles.clear();
        roles.add(role6);
        Hero hero7 = hero("Gingerbread Man", Race.GNOME, roles, 100L);
        roles.clear();
        roles.add(role1);
        roles.add(role6);
        Hero hero8 = hero("Ogre Fiona", Race.ORC, roles, 2000L);
        roles.clear();
        Hero hero9 = hero("Human Peasant", Race.HUMAN, roles, 5L);
        Hero hero10 = hero("Elf Peasant", Race.ELF, roles, 10L);
        logger.debug("Heroes loaded");

        Set<Hero> heroes = new HashSet<>();
        heroes.add(hero1);
        heroes.add(hero2);
        heroes.add(hero3);
        Group group1 = group("Shrek and co.", heroes);
        heroes.clear();
        heroes.add(hero4);
        heroes.add(hero7);
        heroes.add(hero8);
        Group group2 = group("The Shrek 2 Squad", heroes);
        heroes.clear();
        heroes.add(hero5);
        heroes.add(hero6);
        Group group3 = group("The Farsquaad", heroes);
        heroes.clear();
        heroes.add(hero10);
        Group group4 = group("The Very Lame Group", heroes);
        heroes.clear();
        Group group5 = group("The Group That No One Wants to Join", heroes);
        logger.debug("Groups loaded");

        User admin = user("admin", "adminpassword", UserType.ADMIN);
        User user1 = user("user1", "user1password", UserType.USER);
        User user2 = user("user2", "user2password", UserType.USER);
        user1.setHero(hero5);
        user2.setHero(hero6);
        logger.debug("Users loaded");
    }

    /* helper methods for the creation of instances */

    private Hero hero(String name, Race race, Set<Role> roles, Long exp) {
        Hero hero = new Hero();
        hero.setName(name);
        hero.setRace(race);
        for(Role r : roles) {
            hero.addRole(r);
        }
        hero.setExperience(exp);
        heroService.create(hero);
        return hero;
    }

    private Troop troop(String name, String mission, Long gold) {
        Troop troop = new Troop();
        troop.setName(name);
        troop.setMission(mission);
        troop.setGold(gold);
        troopService.create(troop);
        return troop;
    }

    private Role role(String name, String description) {
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);
        roleService.create(role);
        return role;
    }

    private Group group(String name, Set<Hero> heroes) {
        Group group = new Group();
        group.setName(name);
        for (Hero h : heroes) {
            group.addHero(h);
            h.setGroup(group);
            heroService.update(h);
        }
        groupService.create(group);
        return group;
    }

    private User user(String login, String password, UserType userType) {
        User user = new User();
        user.setLogin(login);
        user.setPasswordHash(password);
        user.setType(userType);
        userService.create(user, password);
        return user;
    }

}
