
package jpabook.jpashop.Controller;

import jpabook.jpashop.Domain.Address;
import jpabook.jpashop.Domain.Member;
import jpabook.jpashop.Dto.MemberForm;
import jpabook.jpashop.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {

        model.addAttribute("memberForm", new MemberForm());

        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createMember(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = Address.builder().zipcode(form.getZipcode()).street(form.getStreet()).city(form.getCity()).build();
        Member member = Member.builder().name(form.getName()).address(address).build();

        try {
            memberService.join(member);
        } catch (IllegalStateException e) {
            result.addError(new FieldError("memberForm", "name", e.getMessage()));
            return "members/createMemberForm";
        }

        return "redirect:/";
    }
}
